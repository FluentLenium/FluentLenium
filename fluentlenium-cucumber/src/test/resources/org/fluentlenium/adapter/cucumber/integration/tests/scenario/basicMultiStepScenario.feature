@inheritance
Feature: basic test with steps into multi classes with one driver per scenario

  Scenario: scenario 1 multi with inheritance
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page

  Scenario: scenario 2 multi with inheritance
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page