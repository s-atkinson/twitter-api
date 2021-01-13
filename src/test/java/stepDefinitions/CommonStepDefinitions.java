package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class CommonStepDefinitions {

    private final String QUOTABLE = "\"([^\"]*)\"";
    private CommonState commonState;

    public CommonStepDefinitions (CommonState commonState){
        this.commonState = commonState;
    }

    @Given("^an authorized " + QUOTABLE + " user$")
    public void getAuthorizedUser(String userType) {
        commonState.commonUtils.getAuthorizedUser(userType);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint$")
    public void requestWithEndpoint(String requestType, String endpoint) {
        commonState.commonUtils.requestWithEndpoint(requestType, endpoint);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with path parameter " + QUOTABLE + "$")
    public void requestWithPathParam(String requestType, String endpoint, String pathValue) {
        commonState.commonUtils.requestWithPathParam(requestType, endpoint, pathValue);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with body " + QUOTABLE + "$")
    public void requestWithBody(String requestType, String endpoint, String body) {
        commonState.commonUtils.requestWithBody(requestType, endpoint, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters keys?:" + QUOTABLE + " values?:" + QUOTABLE + "$")
    public void requestWithQueryParams(String requestType, String endpoint, String key, String value) {
        commonState.commonUtils.requestWithQueryParams(requestType, endpoint, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with path parameter " + QUOTABLE + " and with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + "$")
    public void requestWithPathParamQueryParams(String requestType, String endpoint, String pathValue, String key, String value) {
        commonState.commonUtils.requestWithPathParamQueryParams(requestType, endpoint, pathValue, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + " and body " + QUOTABLE + "$")
    public void requestWithQueryParamsAndBody(String requestType, String endpoint, String key, String value, String body) {
        commonState.commonUtils.requestWithQueryParamsAndBody(requestType, endpoint, key, value, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with values? " + QUOTABLE + " in body " + QUOTABLE + "$")
    public void requestWithValueInBody(String requestType, String endpoint, String value, String body) {
        commonState.commonUtils.requestWithValueInBody(requestType, endpoint, value, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + " and values? " + QUOTABLE + " in body " + QUOTABLE + "$")
    public void requestWithQueryParamsAndValueInBody(String requestType, String endpoint, String paramKey, String paramValue, String value, String body) {
        commonState.commonUtils.requestWithQueryParamsAndValueInBody(requestType, endpoint, paramKey, paramValue, value, body);
    }

    @Then("^the response status code is (\\d+)$")
    public void verifyStatusCode(int statusCode) {
        commonState.commonUtils.verifyStatusCode(statusCode);
    }

    @Then("^the response status message contains " + QUOTABLE + "$")
    public void verifyStatusMessage(String statusMessage) {
        commonState.commonUtils.verifyStatusMessage(statusMessage);
    }

    @Then("^the response body contains list " + QUOTABLE + " of size " + QUOTABLE + "$")
    public void verifyListSize(String list, int size) throws Exception {
        commonState.commonUtils.verifyListSize(list, size);
    }

    @Then("^the response body is equal to " + QUOTABLE + "$")
    public void verifyResponseBody(String body) throws IOException {
        commonState.commonUtils.verifyResponseBody(body);
    }

    @Then("^the response body contains string " + QUOTABLE + "$")
    public void verifyResponseBodyString(String string) {
        commonState.commonUtils.verifyResponseBodyString(string);
    }

    @Then("^the response schema is equal to " + QUOTABLE + "$")
    public void verifyResponseSchema(String schema) {
        commonState.commonUtils.verifyResponseSchema(schema);
    }

    @Then("^the response body contains keys?:" + QUOTABLE + " with values?:" + QUOTABLE + "$")
    public void verifyResponseKeyValuePairs(String key, String value) {
        commonState.commonUtils.verifyResponseKeyValuePairs(key, value);
    }

    @Then("^store the response value with key:" + QUOTABLE + " as " + QUOTABLE + "$")
    public void storeResponseValue(String key, String storedResponseKey) {
        commonState.commonUtils.storeResponseValue(key, storedResponseKey);
    }

}
