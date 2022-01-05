Feature: Proposal API


  @development
  Scenario: To validate the Successful Proposal Request
    Given I Authenticate to the ODS System
    When I Proposal a ODS Product
    Then I should able to get the successful response
