package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepHelpers.TwitterUtils;

import java.io.IOException;


public class TwitterStepDefinitions {

    private final String QUOTABLE = "\"([^\"]*)\"";
    private CommonState commonState;
    private TwitterUtils twitterUtils = new TwitterUtils();
    private static Logger logger = LogManager.getLogger(TwitterStepDefinitions.class);

    public TwitterStepDefinitions (CommonState commonState){
        this.commonState = commonState;
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " twitter endpoint$")
    public void requestWithEndpoint(String requestType, String endpoint) {
        endpoint = twitterUtils.getEndpoint(endpoint);
        commonState.commonUtils.requestWithEndpoint(requestType, endpoint);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " twitter endpoint with query parameters key:" + QUOTABLE + " value:" + QUOTABLE + "$")
    public void requestWithQueryParams(String requestType, String endpoint, String key, String value) {
        endpoint = twitterUtils.getEndpoint(endpoint);
        commonState.commonUtils.requestWithQueryParams(requestType, endpoint, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with values? " + QUOTABLE + " in pojo " + QUOTABLE + "$")
    public void requestWithPojo(String requestType, String endpoint, String value, String pojo) {
        if (pojo.equals("PostRuleRequest")) {
            pojo = twitterUtils.getPostRuleRequest(value);
            logger.info("Updated body is equal to " + pojo);
        }
        endpoint = twitterUtils.getEndpoint(endpoint);
        commonState.commonUtils.requestWithJsonString(requestType, endpoint, pojo);
    }

    @When("^I delete the rule from the latest response$")
    public void deleteRule() throws IOException {
        String response = commonState.commonUtils.response.asString();
        String endpoint = twitterUtils.getEndpoint("RULES");
        String body = twitterUtils.deleteRule(response);
        commonState.commonUtils.requestWithJsonString("POST", endpoint, body);
    }

    @Then("^the response body is equal to pojo " + QUOTABLE + "$")
    public void verifyResponseBody(String body) {
        Response response = commonState.commonUtils.response;
        twitterUtils.verifyResponseBody(response, body);
    }
}
