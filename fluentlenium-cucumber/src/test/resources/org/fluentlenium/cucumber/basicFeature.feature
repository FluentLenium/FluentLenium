Feature: basic test with only one driver per feature

  Scenario: scenario 1
    Given feature I am on the first page
    When feature I click on next page
    Then feature I am on the second page

  Scenario: scenario 2
    Given feature I am on the first page
    When feature I click on next page
    Then feature I am on the second page