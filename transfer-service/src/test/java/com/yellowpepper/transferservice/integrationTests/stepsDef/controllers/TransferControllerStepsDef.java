package com.yellowpepper.transferservice.integrationTests.stepsDef.controllers;

import com.yellowpepper.transferservice.integrationTests.IntegrationTests;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class TransferControllerStepsDef {
    @When("the client call to {string}")
    public void the_client_call_to(String path) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the client receives status code {int}")
    public void the_client_receives_status_code(Integer statusCode) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the client receiver {string} message")
    public void the_client_receiver_hello_world_message(String message) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
