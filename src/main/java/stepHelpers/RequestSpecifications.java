package stepHelpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RequestSpecifications {

    private String propFile = "config.properties";
    private RequestSpecification reqSpec;

    public RequestSpecification getRequestSpec() {
        return reqSpec;
    }

    public void getHostAPI(String userType) {
        reqSpec = null;
        switch (userType) {
            case "twitter_v1.1": {
                reqSpec = getTwitter_v1();
                break;
            }
            case "twitter_v2": {
                reqSpec = getTwitter_v2();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
    }

    private RequestSpecification getTwitter_v1() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile);
            prop.load(inputStream);
            String consumerKey = prop.getProperty("CONSUMER_KEY");
            String consumerSecret = prop.getProperty("CONSUMER_SECRET");
            String accessToken = prop.getProperty("ACCESS_TOKEN");
            String tokenSecret = prop.getProperty("TOKEN_SECRET");
            reqSpec = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/1.1/").build().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).header("Content-type", "application/json");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reqSpec;
    }

    private RequestSpecification getTwitter_v2() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile);
            prop.load(inputStream);
            String bearerToken = prop.getProperty("BEARER_TOKEN");
            reqSpec = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/2/").build().header("Authorization", bearerToken).header("Content-type", "application/json");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reqSpec;
    }
}
