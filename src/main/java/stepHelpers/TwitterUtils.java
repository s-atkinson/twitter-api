package stepHelpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.GetTweetResponse;
import model.PostRuleRequest;
import model.PostRuleRequestItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class TwitterUtils {

    private CommonUtils commonUtils = new CommonUtils();
    private static Logger logger = LogManager.getLogger(TwitterUtils.class);
    private GetTweetResponse getTweetResponse = new GetTweetResponse();
    private PostRuleRequest postRuleRequest = new PostRuleRequest();
    private Response response;

    public void requestWithEndpoint(String requestType, RequestSpecification reqSpec, String endpoint) {
        Endpoints ep = Endpoints.valueOf(endpoint);
        String location = ep.getEndpoint();
        logger.info("Endpoint location is equal to " + location);
        commonUtils.requestWithEndpoint(requestType, reqSpec, location);
    }

    public void requestWithQueryParams(String requestType, RequestSpecification reqSpec, String endpoint, String key, String value) {
        Endpoints ep = Endpoints.valueOf(endpoint);
        String location = ep.getEndpoint();
        logger.info("Endpoint location is equal to " + location);
        commonUtils.requestWithQueryParams(requestType, reqSpec, location, key, value);
    }

    public void requestWithValueInBody(String requestType, RequestSpecification reqSpec, String endpoint, String value, String body) {
        if (body.equals("PostRuleRequest")) {
            getPostRuleRequest(value);
            logger.info("Updated body is equal to " + postRuleRequest);
        }
        Endpoints ep = Endpoints.valueOf(endpoint);
        String location = ep.getEndpoint();
        logger.info("Endpoint location is equal to " + location);
        logger.info("Sending " + requestType + " request to the " + location + " endpoint with body " + body);
        if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).body(postRuleRequest).when().post(location).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void deleteRule(RequestSpecification reqSpec, Response response) throws IOException {
        String responseBody = response.asString();
        String ruleId = new JsonPath(responseBody).get("data.id").toString();
        if (ruleId.startsWith("[") && ruleId.endsWith("]")) {
            ruleId = ruleId.substring(1, ruleId.length() - 1);
        }
        Endpoints ep = Endpoints.valueOf("RULES");
        String location = ep.getEndpoint();
        StringBuilder sb = new StringBuilder(new String(Files.readAllBytes(Paths.get("src/test/resources/testData/requests/deleteRuleRequest.json"))));
        String deleteRule = commonUtils.updateBodyWithValue(sb, "{var1}", ruleId);
        logger.info("Deleting rule with id: " + ruleId);
        response = given().spec(reqSpec).body(deleteRule).when().post(location).then().log().all().extract().response();
        CommonState.setResponse(response);
    }

    private void getPostRuleRequest(String value) {
        String[] values = value.split(",");
        PostRuleRequestItems postRuleRequestItems = new PostRuleRequestItems();
        postRuleRequestItems.setValue(values[0]);
        postRuleRequestItems.setTag(values[1]);

        ArrayList<PostRuleRequestItems> itemList = new ArrayList<>();
        itemList.add(postRuleRequestItems);
        postRuleRequest.setAdd(itemList);
    }

    public void verifyResponseBody(Response response, String body) {
        if (body.equals("GetTweetResponse")) {
            logger.info("Response string is: " + response.asString());
            getTweetResponse = response.as(getTweetResponse.getClass());
            String id = "1336443999071195137";
            String text = "API Test Tweet #6569";
            Assert.assertEquals(id, getTweetResponse.getData().getId());
            Assert.assertEquals(text, getTweetResponse.getData().getText());
        }
    }
}
