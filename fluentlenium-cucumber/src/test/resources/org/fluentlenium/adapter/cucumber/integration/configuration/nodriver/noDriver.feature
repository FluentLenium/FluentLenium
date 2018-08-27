Feature: No driver

  Scenario: No driver should start without Before and After hooks
    When I get WebDriver current state
    Then it should be null

  Scenario: Sharing state between steps
    Given I have important string
    When I change content of string
    Then it should be correctly changed