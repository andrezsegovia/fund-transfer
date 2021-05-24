Feature: Client can transfer funds

  A client should be able to transfer funds from his/her account to another account. Hi/She can only make three
  transfers per day. If an account does not have enough funds then the transfer attempt should fail and inform
  the client. If the client exceeds the three transfer per day should be informed and the transfer should be cancel.

  Scenario: Client performs a fund transfer successfully
    Given a client with account number 12345600
    And a funds amount of 1000 USD
    When wants to make fund transfer of 500 USD
    And to the account number 12345601
    And with the description
      """
      Hey dude! I am sending you the money you loaned to me last week.
      """
    And makes a POST call to "/"
    Then transfer should be success with a 200 HTTP Status Code
    And returns a JSON response
      """
      {
        "status": "OK",
        "errors": [],
        "tax_collected": 50.00,
        "CAD": 66,928861615
      }
      """

  Scenario: Client perform a fund transfer without enough funds

  Scenario: Client perform a fourth fund transfer

  Scenario: Client should be charge 0.2% per success transfer

  Scenario: Client should be charge 0.5% per success transfer if amount is grater than 100 USD



