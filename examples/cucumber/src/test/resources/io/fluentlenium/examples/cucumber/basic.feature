Feature: basic

  Scenario: scenario1
    Given Visit duckduckgo
    When I search FluentLenium
    Then Title contains FluentLenium
