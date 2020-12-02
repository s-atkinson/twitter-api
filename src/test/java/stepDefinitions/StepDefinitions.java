package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    @Given("^an authorized twitter_v2 user$")
    public void getAuthorizedUser(){
        System.out.println("defined step");
    }

    @When("^I send a POST request to the endpoint with body$")
    public void postRequestWithBody(){
        System.out.println("defined step");
    }

    @Then("^the response statuscode is 200$")
    public void getStatusCode(){
        System.out.println("defined step");
    }

    @Then("^the response body contains key: with value:$")
    public void verifyResponseBody(){
        System.out.println("defined step");
    }
}
