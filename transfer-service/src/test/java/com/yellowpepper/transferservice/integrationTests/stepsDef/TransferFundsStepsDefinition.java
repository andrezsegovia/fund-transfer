package com.yellowpepper.transferservice.integrationTests.stepsDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.componets.ClientAPIImpl;
import com.yellowpepper.transferservice.integrationTests.IntegrationTests;
import com.yellowpepper.transferservice.integrationTests.commons.HttpClient;
import com.yellowpepper.transferservice.integrationTests.commons.MapToJson;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class TransferFundsStepsDefinition extends IntegrationTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientAPI clientAPI = new ClientAPIImpl();

    private Map<String, Object> transferRequestBody = new HashMap<>();

    @Given("a client with account number {string}")
    public void a_client_with_account_number(String accountNumber) {
        transferRequestBody.put("origin_account", accountNumber);
    }

    @Given("a funds amount of {int} USD")
    public void a_funds_amount_of_usd(Integer fundAmount) {

    }

    @When("wants to make fund transfer of {int} {string}")
    public void wants_to_make_fund_transfer_of_usd(Integer amount, String currency) {
        transferRequestBody.put("amount", amount);
        transferRequestBody.put("currency", currency);
    }

    @When("to the account number {string}")
    public void to_the_account_number(String destinationAccount) {
        transferRequestBody.put("destination_account", destinationAccount);
    }

    @When("with the description")
    public void with_the_description(String description) {
        transferRequestBody.put("description", description);
    }

    @When("makes a POST call to {string}")
    public void makes_a_post_call_to(String url) throws IOException {
        assertNotNull(restTemplate);
        AccountResponse accountResponse = AccountResponse.builder().status("OK").errors(new String[]{}).build();
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("account", transferRequestBody.get("origin_account"));
        requestBody.put("amount", transferRequestBody.get("amount"));
        Mockito.when(restTemplate.postForObject("http://localhost:8082/", requestBody, AccountResponse.class))
                .thenReturn(accountResponse);
        post("http://localhost:8082" + url, MapToJson.convertToJSON(transferRequestBody));
    }

    @Then("transfer should be success with a {int} HTTP Status Code")
    public void transfer_should_be_success_with_a_http_status_code(int statusCode) throws IOException {
        int currentStatusCode = latestResponse.getTheResponse().getStatusCode().value();
        assertEquals(statusCode, currentStatusCode);
    }

    @Then("returns a JSON response")
    public void returns_a_json_response(TransferResponse expectedResponse) throws JsonProcessingException {
        TransferResponse currentTransferResponse = MapToJson.convert(latestResponse.getBody(), TransferResponse.class);
        assertEquals(expectedResponse, currentTransferResponse);
    }
}
