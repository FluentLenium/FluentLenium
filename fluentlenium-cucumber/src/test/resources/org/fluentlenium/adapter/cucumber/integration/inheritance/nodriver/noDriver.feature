Feature: No driver

  Scenario: No driver should start without Before and After hooks
    When I get WebDriver current state
    Then it should be null
    And it should be created instance of @Page

  @fluent
  Scenario: Driver should start when tag is present above scenario
    When I get WebDriver current state
    Then it should not be null with tag
    And it should be created instance of @Page

  Scenario: Sharing state between steps
    Given I have important string
    When I change content of string
    Then it should be correctly changed