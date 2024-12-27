Feature: Test google search

  @severity=blocker
  Scenario: search for celebrity
    Given I request 3 random people from random user service
    Given I store those people to database
    When I request database for a random person
    Then I can see that person's name
    Given I load google page
    And I accept Google cookies if present
    When I search for random person
    Then I can see at least 3 search results

