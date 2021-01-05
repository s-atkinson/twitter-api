package stepDefinitions;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepHelpers.CommonState;
import stepHelpers.CommonUtils;

public class Hooks {

    private CommonUtils commonUtils = new CommonUtils();
    private static Logger logger = LogManager.getLogger(Hooks.class);
    private RequestSpecification reqSpec = CommonState.getRequestSpecification();
    private Response response = CommonState.getResponse();


    @After("@common or @twitter")
    public void tearDownTweet(Scenario scenario) {
        if ((scenario.isFailed())) {
            logger.info("Scenario failed... attempting to delete unneeded tweets...");
            commonUtils.getAuthorizedUser("twitter_v1.1");
            commonUtils.requestWithQueryParams("GET", reqSpec, "users/show.json", "user_id", "749410536517988352");
            commonUtils.storeResponseValue(response, "status.id", "{TweetId}");
            String storedValue = commonUtils.getStoredValue("{TweetId}");
            if (storedValue.equals("1336443999071195137")) {
                logger.info("Unneeded tweets deleted in after scenario");
            } else {
                commonUtils.requestWithPathParam("POST", reqSpec, "statuses/destroy/{key}.json", "{TweetId}");
                logger.info("Successfully deleted unneeded tweet");
                tearDownTweet(scenario);
            }
        }
    }

    @After("@common or @twitter")
    public void tearDownRule(Scenario scenario) {
        if ((scenario.isFailed())) {
            logger.info("Scenario failed... attempting to delete unneeded rules...");
            commonUtils.getAuthorizedUser("twitter_v2");
            commonUtils.requestWithEndpoint("GET", reqSpec, "tweets/search/stream/rules");
            int ruleList = commonUtils.response.jsonPath().getList("data.id").size();
            for (int i = 0; i < ruleList; i++) {
                commonUtils.storeResponseValue(response, "data.id[" + i + "]", "{RuleId}");
                String storedValue = commonUtils.getStoredValue("{RuleId}");
                if (storedValue.equals("1344073430191247362") || storedValue.equals("1344073891946360832")) {
                    logger.info("Rule " + storedValue + " is needed, therefore not deleted...");
                } else {
                    commonUtils.requestWithValueInBody("POST", reqSpec, "tweets/search/stream/rules", "{RuleId}", "deleteRuleRequest");
                    tearDownRule(scenario);
                }
            }
            logger.info("All unneeded rules deleted...");
        }
    }

    /*@After("@twitter")
    public void deleteSpecificRule(Scenario scenario) {
        if ((scenario.isFailed())) {
            commonUtils.getAuthorizedUser("twitter_v2");
            commonUtils.requestWithValueInBody("POST", reqSpec, "tweets/search/stream/rules", "1346250977205243910", "deleteRuleRequest");
        }
    }*/

    /*@After("@twitter")
    public void tearDownTwitter(Scenario scenario) {
        if ((scenario.isFailed()) || (!scenario.isFailed())) {
            httpRequest.getHostAPI("twitter_v1.1");
            commonUtils.requestWithQueryParams("GET", "users/show.json", "user_id", "749410536517988352");
            commonUtils.storeResponseValue("status.id", "{id}");
            String storedValue = commonUtils.getStoredValue("{id}");
            if (storedValue.equals("1336443999071195137")) {
                logger.info("Tweets deleted after scenario");
            } else {
                commonUtils.requestWithPathParam("POST", "statuses/destroy/{key}.json", "{id}");
                logger.info("Successfully deleted tweet using after tag");
                tearDownTwitter(scenario);
            }
        }
    }*/
}