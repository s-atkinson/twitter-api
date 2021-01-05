Feature: Reusable Steps
  The steps in this file should be generic enough to be used in different projects

  @common
  Scenario: Get tweet basic
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/1336443999071195137" endpoint
    Then the response status code is 200

  @common
  Scenario: Get tweet basic 2
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/1336443999071195137" endpoint
    Then the response status code is 200
    And the response body contains key:"data.id" with value:"1336443999071195137"

  @common
  Scenario: Get tweet basic 3
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/1336443999071195137" endpoint
    Then the response status code is 200
    And the response body is equal to "getTweetResponse"
    And the response schema is equal to "getTweetResponse_Schema"

  @common
  Scenario: Get tweet with query parameters
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/1336443999071195137" endpoint with query parameters key:"tweet.fields" value:"public_metrics,author_id,created_at"
    Then the response status code is 200

  @common
  Scenario: Get tweet with query parameters 2
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/1336443999071195137" endpoint with query parameters key:"tweet.fields" value:"public_metrics,author_id,created_at"
    Then the response status code is 200
    And the response body is equal to "getTweetResponse_tweetFields"
    And the response schema is equal to "getTweetResponse_tweetFields_Schema"

  @common
  Scenario: Get tweet with multiple parameters
    Given an authorized "twitter_v2" user
    And I send a "GET" request to the "tweets/1336443999071195137" endpoint with query parameters keys:"tweet.fields&&expansions" values:"public_metrics,author_id,created_at&&author_id"
    Then the response status code is 200
    And the response status message contains "OK"
    And the response body contains list "includes.users" of size "1"
    And the response body contains keys:"data.public_metrics.retweet_count&&data.author_id" with values:"0&&749410536517988352&&"
    And the response body contains string "like_count"

  @common
  Scenario: Post tweet - delete tweet - get tweet
    Given an authorized "twitter_v1.1" user
    When I send a "POST" request to the "statuses/update.json" endpoint with query parameters key:"status" value:"Test Tweet 1"
    Then the response status code is 200
    And the response body contains key:"text" with value:"Test Tweet 1"
    And store the response value with key:"id" as "{id}"
    Then I send a "POST" request to the "statuses/destroy/{key}.json" endpoint with path parameter "{id}"
    And the response status code is 200
    Then I send a "GET" request to the "tweets/{key}" endpoint with path parameter "{id}"
    And the response status code is 404
    And the response status message contains "HTTP/1.1 404 Not Found"

  @common
  Scenario: Post tweet - favourite tweet - delete tweet
    Given an authorized "twitter_v1.1" user
    When I send a "POST" request to the "statuses/update.json" endpoint with query parameters key:"status" value:"Test Tweet 2"
    Then the response status code is 200
    And the response body contains string "id"
    And store the response value with key:"id" as "{id}"
    Then I send a "POST" request to the "favorites/{key}.json" endpoint with path parameter "create" and with query parameters key:"id" value:"{id}"
    And the response body contains key:"favorite_count&&favorited" with value:"1&&true"
    Then I send a "POST" request to the "statuses/destroy/{key}.json" endpoint with path parameter "{id}"
    And the response status code is 200
    And the response status message contains "HTTP/1.1 200 OK"

  @common
  Scenario: Get rule
    Given an authorized "twitter_v2" user
    When I send a "GET" request to the "tweets/search/stream/rules" endpoint
    Then the response status code is 200
    And the response status message contains "OK"
    And the response body contains list "data" of size "2"
    And the response body contains key:"data.value[0]" with value:"Cat in a hat"

  @common
  Scenario: Post rule - delete rule
    Given an authorized "twitter_v2" user
    When I send a "POST" request to the "tweets/search/stream/rules" endpoint with body "postRuleRequest"
    Then the response status code is 201
    And the response status message contains "HTTP/1.1 201 Created"
    And the response body contains key:"meta.summary.created" with value:"1"
    And the response body contains key:"data.value" with value:"[Test]"
    And store the response value with key:"data.id" as "{id}"
    When I send a "POST" request to the "tweets/search/stream/rules" endpoint with value "{id}" in body "deleteRuleRequest"
    Then the response status code is 200
    And the response body contains key:"meta.summary.deleted" with value:"1"

  @common
  Scenario: Post 2 rules - delete 2 rules
    Given an authorized "twitter_v2" user
    When I send a "POST" request to the "tweets/search/stream/rules" endpoint with query parameters keys:"dry_run" values:"false" and body "postRuleRequest"
    Then the response status code is 201
    And store the response value with key:"data.id" as "{id}"
    When I send a "POST" request to the "tweets/search/stream/rules" endpoint with query parameters keys:"dry_run" values:"false" and body "postRuleRequest2"
    Then the response status code is 201
    And store the response value with key:"data.id" as "{id2}"
    When I send a "POST" request to the "tweets/search/stream/rules" endpoint with query parameters keys:"dry_run" values:"false" and values "{id},{id2}" in body "deleteRuleRequest2"
    Then the response status code is 200
    And the response body contains key:"meta.summary.deleted" with value:"2"
