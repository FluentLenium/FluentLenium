@noinheritance
Feature: basic test with one driver per scenario without inheritance

  Scenario: scenario 1 without inheritance
    Given scenario I am on the first page
    When scenario I click on next page
    Then scenario I am on the second page

  Scenario: scenario 2 without inheritance
    Given scenario I am on the first page
    When scenario I click on next page
    Then scenario I am on the second page