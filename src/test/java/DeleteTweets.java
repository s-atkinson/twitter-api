import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeleteTweets {

    RequestSpecification rs = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/")
            .build().auth().oauth(Keys.CONSUMER_KEY,Keys.CONSUMER_SECRET,Keys.ACCESS_TOKEN,Keys.TOKEN_SECRET);

    @Test
    public void deleteAllTweets() {
        String latestTweet = given().log().all().spec(rs).basePath("1.1/users/").queryParam("user_id", "749410536517988352").when().get("show.json").then()
                .log().all().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(latestTweet);
        String tweetId = js.getString("status.id");
        System.out.println(tweetId);

        if (tweetId == null) {
            System.out.println("All tweets have been deleted");
        } else {
            given().spec(rs).basePath("1.1/statuses/destroy/").when().post(tweetId + ".json").then().statusCode(200).extract().response();
            System.out.println("Successfully deleted tweet with id: " + tweetId + "\n");
            deleteAllTweets();
        }
    }
}
