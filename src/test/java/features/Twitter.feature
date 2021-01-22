Feature: Twitter

  @twitter
  Scenario: Get tweet
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "GET_TWEET_ID" twitter endpoint
    Then the response status code is 200
    Then the response body is equal to pojo "GetTweetResponse"

  @twitter
  Scenario: Get tweet 2
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "GET_TWEET" twitter endpoint with query parameters key:"ids" value:"1336443999071195137"
    Then the response status code is 200
    Then the response body is equal to "GetTweetResponse_queryParam"

  @twitter
  Scenario: Add/delete rule with pojo and enums for endpoint
  Given an authorized "twitter_v2" user
  When I send a "POST" request to the "RULES" endpoint with values "pojo test, this is a pojo test" in pojo "PostRuleRequest"
  Then the response status code is 201
  And I delete the rule from the latest response
  Then the response status code is 200
  And the response body contains key:"meta.summary.deleted" with value:"1"

