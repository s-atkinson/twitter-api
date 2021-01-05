package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepHelpers.CommonState;
import stepHelpers.CommonUtils;

import java.io.IOException;

public class CommonStepDefinitions {

    private final String QUOTABLE = "\"([^\"]*)\"";
    private CommonUtils commonUtils = new CommonUtils();
    private RequestSpecification reqSpec;
    private Response response;

    @Given("^an authorized " + QUOTABLE + " user$")
    public void getAuthorizedUser(String userType) {
        commonUtils.getAuthorizedUser(userType);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint$")
    public void requestWithEndpoint(String requestType, String endpoint) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithEndpoint(requestType, reqSpec, endpoint);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with path parameter " + QUOTABLE + "$")
    public void requestWithPathParam(String requestType, String endpoint, String pathValue) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithPathParam(requestType, reqSpec, endpoint, pathValue);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with body " + QUOTABLE + "$")
    public void requestWithBody(String requestType, String endpoint, String body) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithBody(requestType, reqSpec, endpoint, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters keys?:" + QUOTABLE + " values?:" + QUOTABLE + "$")
    public void requestWithQueryParams(String requestType, String endpoint, String key, String value) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithQueryParams(requestType, reqSpec, endpoint, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with path parameter " + QUOTABLE + " and with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + "$")
    public void requestWithPathParamQueryParams(String requestType, String endpoint, String pathValue, String key, String value) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithPathParamQueryParams(requestType, reqSpec, endpoint, pathValue, key, value);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + " and body " + QUOTABLE + "$")
    public void requestWithQueryParamsAndBody(String requestType, String endpoint, String key, String value, String body) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithQueryParamsAndBody(requestType, reqSpec, endpoint, key, value, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with values? " + QUOTABLE + " in body " + QUOTABLE + "$")
    public void requestWithValueInBody(String requestType, String endpoint, String value, String body) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithValueInBody(requestType, reqSpec, endpoint, value, body);
    }

    @When("^I send a " + QUOTABLE + " request to the " + QUOTABLE + " endpoint with query parameters? keys?:" + QUOTABLE + " values?:" + QUOTABLE + " and values? " + QUOTABLE + " in body " + QUOTABLE + "$")
    public void requestWithQueryParamsAndValueInBody(String requestType, String endpoint, String paramKey, String paramValue, String value, String body) {
        reqSpec = CommonState.getRequestSpecification();
        commonUtils.requestWithQueryParamsAndValueInBody(requestType, reqSpec, endpoint, paramKey, paramValue, value, body);
    }

    @Then("^the response status code is (\\d+)$")
    public void verifyStatusCode(int statusCode) {
        response = CommonState.getResponse();
        commonUtils.verifyStatusCode(response, statusCode);
    }

    @Then("^the response status message contains " + QUOTABLE + "$")
    public void verifyStatusMessage(String statusMessage) {
        response = CommonState.getResponse();
        commonUtils.verifyStatusMessage(response, statusMessage);
    }

    @Then("^the response body contains list " + QUOTABLE + " of size " + QUOTABLE + "$")
    public void verifyListSize(String list, int size) throws Exception {
        response = CommonState.getResponse();
        commonUtils.verifyListSize(response, list, size);
    }

    @Then("^the response body is equal to " + QUOTABLE + "$")
    public void verifyResponseBody(String body) throws IOException {
        response = CommonState.getResponse();
        commonUtils.verifyResponseBody(response, body);
    }

    @Then("^the response body contains string " + QUOTABLE + "$")
    public void verifyResponseBodyString(String string) {
        response = CommonState.getResponse();
        commonUtils.verifyResponseBodyString(response, string);
    }

    @Then("^the response schema is equal to " + QUOTABLE + "$")
    public void verifyResponseSchema(String schema) {
        response = CommonState.getResponse();
        commonUtils.verifyResponseSchema(response, schema);
    }

    @Then("^the response body contains keys?:" + QUOTABLE + " with values?:" + QUOTABLE + "$")
    public void verifyResponseKeyValuePairs(String key, String value) {
        response = CommonState.getResponse();
        commonUtils.verifyResponseKeyValuePairs(response, key, value);
    }

    @Then("^store the response value with key:" + QUOTABLE + " as " + QUOTABLE + "$")
    public void storeResponseValue(String key, String storedResponseKey) {
        response = CommonState.getResponse();
        commonUtils.storeResponseValue(response, key, storedResponseKey);
    }

}
