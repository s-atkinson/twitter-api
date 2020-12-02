Feature: Reusable Steps
  The steps in this file should be generic enough to be used in different projects

  Scenario: Filtered stream add rule
    Given an authorized twitter_v2 user
    When I send a POST request to the endpoint with body
    Then the response statuscode is 200
    And the response body contains key: with value: