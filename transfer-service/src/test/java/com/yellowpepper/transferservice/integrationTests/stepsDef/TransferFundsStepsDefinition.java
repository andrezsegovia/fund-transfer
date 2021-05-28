package com.yellowpepper.transferservice.integrationTests.stepsDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.yellowpepper.transferservice.integrationTests.IntegrationTests;
import com.yellowpepper.transferservice.integrationTests.commons.MapToJson;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import com.yellowpepper.transferservice.pojos.TransferRequest;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;


@AutoConfigureWireMock(port = 8082)
public class TransferFundsStepsDefinition extends IntegrationTests {

    //TODO read the port from the properties file
    public static WireMockServer wiremock = new WireMockServer(8082);

    private TransferRequest.TransferRequestBuilder transferRequestBuilder;
    private TransferRequest transferRequest;

    @Before
    public void beforeAll() {
        transferRequestBuilder = TransferRequest.builder();
        wiremock.start();
    }
    @AfterStep
    public void after() {
        wiremock.resetAll();
    }
    @After
    public void afterAll() {
        wiremock.shutdown();
    }

    @Given("a client with account number {string}")
    public void a_client_with_account_number(String accountNumber) {
        transferRequestBuilder.originAccount(accountNumber);
    }

    @Given("a funds amount of {float} USD")
    public void a_funds_amount_of_usd(Float fundAmount) {

    }

    @When("wants to make fund transfer of {float} {string}")
    public void wants_to_make_fund_transfer_of_usd(Float amount, String currency) {
        transferRequestBuilder.amount(amount);
        transferRequestBuilder.currency(currency);
    }

    @When("to the account number {string}")
    public void to_the_account_number(String destinationAccount) {
        transferRequestBuilder.destinationAccount(destinationAccount);
    }

    @When("with the description")
    public void with_the_description(String description) {
        transferRequestBuilder.description(description);
    }

    @When("makes a POST call to {string}")
    public void makes_a_post_call_to(String url) throws IOException {
        AccountResponse accountResponse = AccountResponse.builder().status("OK").errors(new String[]{}).build();
        wiremock.stubFor(WireMock.post(urlEqualTo("/"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(accountResponse))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));
        transferRequest = transferRequestBuilder.build();
        post("http://localhost:8080" + url, MapToJson.covertToJSONString(transferRequest));
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
