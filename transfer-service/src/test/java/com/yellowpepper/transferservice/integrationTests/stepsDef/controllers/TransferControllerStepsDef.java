package com.yellowpepper.transferservice.integrationTests.stepsDef.controllers;

import com.yellowpepper.transferservice.integrationTests.IntegrationTests;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

import static org.junit.Assert.*;

public class TransferControllerStepsDef extends IntegrationTests {

    @When("the client call to {string}")
    public void the_client_call_to(String path) throws Throwable {
        get("http://localhost:8080/"+path);
    }
    @Then("the client receives a {int} HTTP Status Code")
    public void the_client_receives_status_code_XXX_ok_http_status(int statusCode) throws IOException {
        int currentStatusCode = latestResponse.getTheResponse().getStatusCode().value();
        assertEquals(statusCode, currentStatusCode);
    }
    @Then("the client receiver {string} message")
    public void the_client_receiver_hello_world_message(String message) throws IOException {
        System.out.println(latestResponse.getBody());
        assertEquals(message, latestResponse.getBody());
    }
}
