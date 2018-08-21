Feature: basic test with steps into multi classes with one driver per feature

  Scenario: scenario 1
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page

  Scenario: scenario 2
    Given feature multi1 I am on the first page
    When feature multi2 I click on next page
    Then feature multi2 I am on the second page