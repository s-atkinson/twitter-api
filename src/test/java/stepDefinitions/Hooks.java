package stepDefinitions;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private CommonState commonState;
    private static Logger logger = LogManager.getLogger(Hooks.class);

    public Hooks(CommonState commonState) {
        this.commonState = commonState;
    }

    @After("@common or @twitter")
    public void tearDownTweet(Scenario scenario) {
        if ((scenario.isFailed())) {
            logger.info("Scenario failed... attempting to delete unneeded tweets...");
            commonState.commonUtils.getAuthorizedUser("twitter_v1.1");
            commonState.commonUtils.requestWithQueryParams("GET", "users/show.json", "user_id", "749410536517988352");
            commonState.commonUtils.storeResponseValue("status.id", "{TweetId}");
            String storedValue = commonState.commonUtils.getStoredValue("{TweetId}");
            if (storedValue.equals("1336443999071195137")) {
                logger.info("Unneeded tweets deleted in after scenario");
            } else {
                commonState.commonUtils.requestWithPathParam("POST", "statuses/destroy/{key}.json", "{TweetId}");
                logger.info("Successfully deleted unneeded tweet");
                tearDownTweet(scenario);
            }
        }
    }

    @After("@common or @twitter")
    public void tearDownRule(Scenario scenario) {
        if ((scenario.isFailed())) {
            logger.info("Scenario failed... attempting to delete unneeded rules...");
            commonState.commonUtils.getAuthorizedUser("twitter_v2");
            commonState.commonUtils.requestWithEndpoint("GET", "tweets/search/stream/rules");
            int ruleList = commonState.commonUtils.response.jsonPath().getList("data.id").size();
            for (int i = 0; i < ruleList; i++) {
                commonState.commonUtils.storeResponseValue("data.id[" + i + "]", "{RuleId}");
                String storedValue = commonState.commonUtils.getStoredValue("{RuleId}");
                if (storedValue.equals("1344073430191247362") || storedValue.equals("1344073891946360832")) {
                    logger.info("Rule " + storedValue + " is needed, therefore not deleted...");
                } else {
                    commonState.commonUtils.requestWithValueInBody("POST", "tweets/search/stream/rules", "{RuleId}", "deleteRuleRequest");
                    tearDownRule(scenario);
                }
            }
            logger.info("All unneeded rules deleted...");
        }
    }

    /*@After("@twitter")
    public void deleteSpecificRule(Scenario scenario) {
        if ((scenario.isFailed())) {
            commonState.commonUtils.getAuthorizedUser("twitter_v2");
            commonState.commonUtils.requestWithValueInBody("POST", "tweets/search/stream/rules", "1346250977205243910", "deleteRuleRequest");
        }
    }

    @After("@twitter")
    public void tearDownTwitter(Scenario scenario) {
        if ((scenario.isFailed()) || (!scenario.isFailed())) {
            commonState.commonUtils.getAuthorizedUser("twitter_v1.1");
            commonState.commonUtils.requestWithQueryParams("GET", "users/show.json", "user_id", "749410536517988352");
            commonState.commonUtils.storeResponseValue("status.id", "{id}");
            String storedValue = commonState.commonUtils.getStoredValue("{id}");
            if (storedValue.equals("1336443999071195137")) {
                logger.info("Tweets deleted after scenario");
            } else {
                commonState.commonUtils.requestWithPathParam("POST", "statuses/destroy/{key}.json", "{id}");
                logger.info("Successfully deleted tweet using after tag");
                tearDownTwitter(scenario);
            }
        }
    }*/
}