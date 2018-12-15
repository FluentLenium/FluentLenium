@noinheritance
Feature: basic test with steps into multi classes without inheritance

  Scenario: scenario 1 multi without inheritance
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page

  Scenario: scenario 2 multi without inheritance
    Given scenario multi1 I am on the first page
    When scenario multi2 I click on next page
    Then scenario multi2 I am on the second page