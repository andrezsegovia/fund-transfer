Feature: Client can transfer funds

  A client should be able to transfer funds from his/her account to another account. Hi/She can only make three
  transfers per day. If an account does not have enough funds then the transfer attempt should fail and inform
  the client. If the client exceeds the three transfer per day should be informed and the transfer should be cancel.

  Scenario: Client performs a fund transfer successfully
    Given a client with account number "12345600"
    And a funds amount of 70000.0 USD
    When wants to make fund transfer of 5000.0 "USD"
    And to the account number "12345601"
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
        "tax_collected": 25.0,
        "CAD": 30.25
      }
      """
  Scenario: Client performs a fund transfer without enough funds
    Given a client with account number "12345600"
    And a funds amount of 1000.0 USD
    When wants to make fund transfer of 1001.0 "USD"
    And to the account number "12345601"
    And with the description
      """
      Hey dude! I am sending you the money you loaned to me last week.
      """
    And makes a POST call to "/" but without enough funds
    Then transfer should be success with a 200 HTTP Status Code
    And returns a JSON response
      """
      {
        "status": "ERROR",
        "errors": ["insufficient-funds"],
        "tax_collected": 0.00
      }
      """
  Scenario: Client perform a fourth fund transfer
    Given a client with account number "12345600"
    And a funds amount of 70000.0 USD
    And with three successful transfers for today
    When wants to make fund transfer of 200.0 "USD"
    And to the account number "12345601"
    And with the description
      """
      Hey dude! I am sending you the money you loaned to me last week.
      """
    And makes a POST call to "/"
    Then transfer should be success with a 200 HTTP Status Code
    And returns a JSON response
      """
      {
        "status": "ERROR",
        "errors": ["limit_exceeded"],
        "tax_collected": 0.00
      }
      """
  Scenario: Client should be charge 0.2% per success transfer
    Given a client with account number "12345600"
    And a funds amount of 70000.0 USD
    When wants to make fund transfer of 50.0 "USD"
    And to the account number "12345601"
    And with the description
      """
      Hey dude! I am sending you the money you loaned to me last week.
      """
    And makes a POST call to "/"
    Then transfer should be success with a 200 HTTP Status Code
    And the tax collected amount should be 0.1 USD

#  Scenario: Client should be charge 0.5% per success transfer if amount is grater than 100 USD



