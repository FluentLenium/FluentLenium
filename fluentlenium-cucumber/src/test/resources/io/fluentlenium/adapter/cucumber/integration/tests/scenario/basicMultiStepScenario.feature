Feature: Basic test with steps into multi classes with one driver per Scenario

  Scenario: Scenario 1 with multi-class step definition
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page

  Scenario: Scenario 2 with multi-class step definition
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page