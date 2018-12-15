@inheritance
Feature: basic test with only one driver per feature with inheritance

  Scenario: scenario 1 per feature with inheritance
    Given feature I am on the first page
    When feature I click on next page
    Then feature I am on the second page

  Scenario: scenario 2 per feature with inheritance
    Given feature I am on the first page
    When feature I click on next page
    Then feature I am on the second page