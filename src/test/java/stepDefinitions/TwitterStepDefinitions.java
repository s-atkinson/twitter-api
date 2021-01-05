package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stepHelpers.CommonState;
import stepHelpers.TwitterUtils;

import java.io.IOException;


public class TwitterStepDefinitions {

    private final String QUOTABLE = "\"([^\"]*)\"";
    private TwitterUtils twitterUtils = new TwitterUtils();
    private RequestSpecification reqSpec;
    private Response response;

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " twitter endpoint$")
    public void requestWithEndpoint(String requestType, String endpoint) {
        reqSpec = CommonState.getRequestSpecification();
        twitterUtils.requestWithEndpoint(requestType, reqSpec, endpoint);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " twitter endpoint with query parameters key:" + QUOTABLE + " value:" + QUOTABLE + "$")
    public void requestWithQueryParams(String requestType, String endpoint, String key, String value) {
        reqSpec = CommonState.getRequestSpecification();
        twitterUtils.requestWithQueryParams(requestType, reqSpec, endpoint, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with values? " + QUOTABLE + " in pojo " + QUOTABLE + "$")
    public void requestWithValueInBody(String requestType, String endpoint, String value, String body) {
        reqSpec = CommonState.getRequestSpecification();
        twitterUtils.requestWithValueInBody(requestType, reqSpec, endpoint, value, body);
    }

    @When("^I delete the rule from the latest response$")
    public void deleteRule() throws IOException {
        reqSpec = CommonState.getRequestSpecification();
        response = CommonState.getResponse();
        twitterUtils.deleteRule(reqSpec, response);
    }

    @Then("^the response body is equal to pojo " + QUOTABLE + "$")
    public void verifyResponseBody(String body) {
        response = CommonState.getResponse();
        twitterUtils.verifyResponseBody(response, body);
    }
}
