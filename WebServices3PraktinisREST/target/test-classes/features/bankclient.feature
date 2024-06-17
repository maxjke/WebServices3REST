Feature: Do I get all clients?


  Scenario:
    When I'm trying to get all bank clients
    Then The response contains the list of expected bank clients
    And The size of the list of bank clients is 4
