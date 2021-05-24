
Feature: Transfer Controller should return 200 when is call
  Scenario: client makes a GET request to transfer endpoint
    When the client call to "/"
    Then the client receives a 200 HTTP Status Code

Feature: Client can transfer funds

  A client should be able to transfer funds from his/her account to another account. Hi/She can only make three
  transfers per day. If an account does not have enough funds then the transfer attempt should fail and inform
  the client. If the client exceeds the three transfer per day should be informed and the transfer should be cancel.

  Scenario: Client performs a fund transfer successfully

  Scenario: Client perform a fund transfer without enough funds

  Scenario: Client perform a fourth fund transfer

  Scenario: Client should be charge 0.2% per success transfer

  Scenario: Client should be charge 0.5% per success transfer if amount is grater than 100 USD



