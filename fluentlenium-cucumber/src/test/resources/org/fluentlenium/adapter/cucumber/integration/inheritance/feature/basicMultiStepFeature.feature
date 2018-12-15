@inheritance
Feature: basic test with steps into multi classes with one driver per feature with inheritance

  Scenario: scenario 1 per feature with inheritance
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page

  Scenario: scenario 2 per feature with inheritance
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page