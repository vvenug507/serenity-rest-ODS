Feature: To Check The Health of Different Services

  @HealthCheck
  Scenario: To validate the status of different services
    Given All Required Service URL in Excel
    Then Execute the Services URL
    When The Execution is complete print the result