package com.yellowpepper.transferservice.integrationTests.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.yellowpepper.transferservice.daos.TransferRepository;
import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.integrationTests.IntegrationTests;
import com.yellowpepper.transferservice.integrationTests.commons.MapToJson;
import com.yellowpepper.transferservice.mappers.TransferRequestMapper;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import com.yellowpepper.transferservice.pojos.TransferRequest;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;


@AutoConfigureWireMock(port = 8082)
public class TransferFundsStepsDefinition extends IntegrationTests {

    //TODO read the port from the properties file
    public static WireMockServer wiremock = new WireMockServer(8082);

    private TransferRequest.TransferRequestBuilder transferRequestBuilder;
    private TransferRequest transferRequest;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferRequestMapper transferRequestMapper;

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
        transferRepository.deleteAll();
        wiremock.shutdown();
    }

    @Given("a client with account number {string}")
    public void a_client_with_account_number(String accountNumber) {
        transferRequestBuilder.originAccount(accountNumber);
    }

    @Given("a funds amount of {float} USD")
    public void a_funds_amount_of_usd(Float fundAmount) {

    }

    @Given("with three successful transfers for today")
    public void with_three_successful_transfers_for_today() {
        transferRepository.save(transferRequestMapper.transferRequestToTransfer(
                transferRequestBuilder
                        .amount(200.0f)
                        .destinationAccount("12345601")
                        .currency("USD")
                        .description("First Transfer")
                        .build()));
        transferRepository.save(transferRequestMapper.transferRequestToTransfer(
                transferRequestBuilder
                        .amount(150.56f)
                        .destinationAccount("111222333")
                        .currency("USD")
                        .description("Second Transfer")
                        .build()));
        transferRepository.save(transferRequestMapper.transferRequestToTransfer(
                transferRequestBuilder
                        .amount(250.32f)
                        .destinationAccount("111555666")
                        .currency("USD")
                        .description("Third Transfer")
                        .build()));

        System.out.println("<--------------------------------------->");
        transferRepository.findAll().forEach(System.out::println);
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

    @When("makes a POST call to {string} but without enough funds")
    public void makes_a_post_call_to_but_without_enough_funds(String url) throws IOException {
        AccountResponse accountResponse = AccountResponse.builder().status("ERROR")
                .errors(new String[]{"insufficient-funds"}).build();
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
    @Then("the tax collected amount should be {float} USD")
    public void the_tax_collected_amount_should_be_usd(Float taxCollected) throws JsonProcessingException {
        TransferResponse currentTransferResponse = MapToJson.convert(latestResponse.getBody(), TransferResponse.class);
        assertEquals(taxCollected, currentTransferResponse.getTaxCollected());
    }





}
