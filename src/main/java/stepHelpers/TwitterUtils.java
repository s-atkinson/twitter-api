package stepHelpers;

import gherkin.deps.com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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

public class TwitterUtils {

    private CommonUtils commonUtils = new CommonUtils();
    private static Logger logger = LogManager.getLogger(TwitterUtils.class);
    private GetTweetResponse getTweetResponse = new GetTweetResponse();
    private PostRuleRequest postRuleRequest = new PostRuleRequest();


    public String getEndpoint(String endpoint) {
        Endpoints ep = Endpoints.valueOf(endpoint);
        String location = ep.getEndpoint();
        logger.info("Endpoint location is equal to " + location);
        return location;
    }

    public String deleteRule(String response) throws IOException {
        String ruleId = new JsonPath(response).get("data.id").toString();
        if (ruleId.startsWith("[") && ruleId.endsWith("]")) {
            ruleId = ruleId.substring(1, ruleId.length() - 1);
            logger.info("Rule Id = " + ruleId);
        }
        StringBuilder sb = new StringBuilder(new String(Files.readAllBytes(Paths.get("src/test/resources/testData/requests/deleteRuleRequest.json"))));
        return commonUtils.updateBodyWithValue(sb, "{var1}", ruleId);
    }

    public String getPostRuleRequest(String value) {
        String[] values = value.split(",");
        PostRuleRequestItems postRuleRequestItems = new PostRuleRequestItems();
        postRuleRequestItems.setValue(values[0]);
        postRuleRequestItems.setTag(values[1]);

        ArrayList<PostRuleRequestItems> itemList = new ArrayList<>();
        itemList.add(postRuleRequestItems);
        postRuleRequest.setAdd(itemList);
        Gson gson = new Gson();
        return gson.toJson(postRuleRequest);
    }

    public void verifyResponseBody(Response response, String body) {
        if (body.equals("GetTweetResponse")) {
            logger.info("Response string is: " + response.asString());
            getTweetResponse = response.as(getTweetResponse.getClass());
            String id = "1336443999071195137";
            String text = "API Test Tweet #6569";
            Assert.assertEquals(id, getTweetResponse.getData().getId());
            Assert.assertEquals(text, getTweetResponse.getData().getText());
        } else throw new IllegalArgumentException("Invalid body");
    }
}
