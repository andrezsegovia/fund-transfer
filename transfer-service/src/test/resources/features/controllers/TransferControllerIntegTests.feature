Feature: Transfer Controller should return 200 when is call
  Scenario: client makes a GET request to transfer endpoint
    When the client call to "/"
    Then the client receives status code 200
    And the client receiver "Hello World" message