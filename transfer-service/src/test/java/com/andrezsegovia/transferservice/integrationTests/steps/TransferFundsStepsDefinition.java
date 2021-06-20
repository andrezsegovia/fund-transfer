package com.andrezsegovia.transferservice.integrationTests.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.andrezsegovia.transferservice.commons.DecimalFormatUtil;
import com.andrezsegovia.transferservice.commons.TaxComponent;
import com.andrezsegovia.transferservice.daos.TransferRepository;
import com.andrezsegovia.transferservice.integrationTests.IntegrationTests;
import com.andrezsegovia.transferservice.integrationTests.commons.MapToJson;
import com.andrezsegovia.transferservice.mappers.TransferRequestMapper;
import com.andrezsegovia.transferservice.pojos.AccountResponse;
import com.andrezsegovia.transferservice.pojos.ExchangeResponse;
import com.andrezsegovia.transferservice.pojos.TransferRequest;
import com.andrezsegovia.transferservice.pojos.TransferResponse;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;


@AutoConfigureWireMock
public class TransferFundsStepsDefinition extends IntegrationTests {

    @Value("${local.account.service.port}")
    protected int localAccountServicePort;

    public WireMockServer wiremock = new WireMockServer(WireMockSpring.options().port(8082));

    private TransferRequest.TransferRequestBuilder transferRequestBuilder;
    private TransferRequest transferRequest;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferRequestMapper transferRequestMapper;

    @Autowired
    private TaxComponent taxComponent;
    
    @Value("${local.server.url}")
    private String localServerURL;

    @Value("${local.account.service.url}")
    private String localAccountServiceURL; 
    
    @Value("${local.exchange.service.url}")
    private String localExchangeServiceURL;

    @Before
    public void before() {
        transferRequestBuilder = TransferRequest.builder();
        //wiremock.start();
    }

    @AfterStep
    public void afterStep() {
       // wiremock.resetAll();
    }

    @After
    public void after() {
        transferRepository.deleteAll();
        //wiremock.shutdown();
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
    public void makes_a_post_call_to(String url) throws IOException, InterruptedException {
        transferRequest = transferRequestBuilder.build();
        wiremock.start();
        AccountResponse accountResponse = AccountResponse.builder()
                .status("OK")
                .errors(new String[]{})
                .build();
        wiremock.stubFor(WireMock.post(urlPathMatching("/account/[a-zA-Z]+"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(accountResponse))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));

        Float exchangeValueMock = taxComponent.calculateTaxAmount(transferRequest.getAmount(),
                taxComponent.calculateTaxPercentage(transferRequest.getAmount())) * 1.21f;
        ExchangeResponse exchangeResponseMock = ExchangeResponse.builder()
                .status("OK")
                .errors(new String[]{})
                .source("USD")
                .output("CAD")
                .value(exchangeValueMock)
                .build();
        wiremock.stubFor(WireMock.get(urlMatching("/exchange/convert\\?source=USD&output=CAD&value=[0-9]+.[0-9]+$"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(exchangeResponseMock))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));
        transferRequest = transferRequestBuilder.build();
        post(localServerURL + url, MapToJson.covertToJSONString(transferRequest));
        wiremock.resetAll();
        wiremock.shutdown();
        Thread.sleep(500); // TODO Wiremock sometimes fails because the next test run fist than the shutdown finishes
    }

    @When("makes a POST call to {string} but without enough funds")
    public void makes_a_post_call_to_but_without_enough_funds(String url) throws IOException, InterruptedException {
        wiremock.start();
        AccountResponse accountResponse = AccountResponse.builder().status("ERROR")
                .errors(new String[]{"insufficient-funds"}).build();
        wiremock.stubFor(WireMock.post(urlPathMatching("/account/[a-zA-Z]+"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(accountResponse))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));
        transferRequest = transferRequestBuilder.build();
        post(localServerURL + url, MapToJson.covertToJSONString(transferRequest));
        wiremock.resetAll();
        wiremock.shutdown();
        Thread.sleep(500); // TODO Wiremock sometimes fails because the next test run fist than the shutdown finishes
    }

    @When("the exchange service fails")
    public void the_exchange_services_fails() throws IOException, InterruptedException {
        wiremock.start();

        AccountResponse accountResponse = AccountResponse.builder().status("OK").errors(new String[]{}).build();
        wiremock.stubFor(WireMock.post(urlPathMatching("/account/[a-zA-Z]+"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(accountResponse))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));


        ExchangeResponse exchangeResponseMock = ExchangeResponse.builder()
                .status("ERROR")
                .errors(new String[]{""}).build();
        wiremock.stubFor(WireMock.get(urlEqualTo("/exchange/convert?source=USD&output=CAD&value=5.0"))
                .willReturn(
                        aResponse()
                                .withBody(MapToJson.covertToJSONString(exchangeResponseMock))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withStatus(HttpStatus.OK.value())));

        transferRequest = transferRequestBuilder.build();
        post(localServerURL, MapToJson.covertToJSONString(transferRequest));

        wiremock.resetAll();
        wiremock.shutdown();
        Thread.sleep(500); // TODO Wiremock sometimes fails because the next test run fist than the shutdown finishes
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
