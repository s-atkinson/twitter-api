import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
//import org.junit.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetTweets {

    RequestSpecification rs = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/").setBasePath("/2/tweets/").build().auth().oauth(Keys.CONSUMER_KEY,Keys.CONSUMER_SECRET,Keys.ACCESS_TOKEN,Keys.TOKEN_SECRET);

    @Test
    public void getTweet() {
        HashMap<String, String> params = new HashMap<>();
        //you can use the following param types
        //.formParams(params)
        //.pathParams(params)
        //.queryParams(params)
        params.put("tweet.fields", "public_metrics,author_id,created_at");

        given().spec(rs).log().all().queryParams(params).when().get("1331012150354178055").then().log().all().assertThat().statusCode(200).extract().response();
    }

    @Test
    public void getLatestTweet(){

    }

    @Test
    public void getTweetOembed(){

    }

    @Test
    public void getHomeTimeline(){

    }

    @Test
    public void getMentionsTimeline(){

    }

    @Test
    public void getUserTimeline(){

    }

    @Test
    public void recentSearch(){

    }
}
