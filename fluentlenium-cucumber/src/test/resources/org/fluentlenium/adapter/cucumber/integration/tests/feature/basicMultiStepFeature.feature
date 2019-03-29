Feature: Basic test with steps into multi classes with one driver per Feature

  Scenario: Scenario 1 per Feature with multi-class step definition
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page

  Scenario: Scenario 2 per Feature with multi-class step definition
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page