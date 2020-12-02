import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Misc {

    RestAssured ra = new RestAssured();
    Payload payload = new Payload();

    RequestSpecification requestUser = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/").setBasePath("1.1/users/")
            .build().auth().oauth(Keys.CONSUMER_KEY, Keys.CONSUMER_SECRET, Keys.ACCESS_TOKEN, Keys.TOKEN_SECRET);

    @Test
    public void getuserId(){
        String response = given().spec(requestUser).log().all()
                .queryParam("screen_name","stewg7").when()
                .get("show.json").then().log().all().statusCode(200).extract().response().asString();

        JsonPath jp = new JsonPath(response);
        String id = jp.getString("id");
        Assert.assertEquals(id,"749410536517988352");
    }

    @Test
    public void postFavourite(){

    }

    @Test
    public void getFavourite(){

    }

    @Test
    public void deleteFavourite(){

    }

    @Test
    public void hideReply() {

    }

    @Test
    public void unhideReply(){

    }

    @Test
    public void postRetweet(){

    }

    @Test
    public void postUnretweet(){

    }

    @Test
    public void getRetweets(){

    }

    @Test
    public void getRetweeters(){

    }

    @Test
    public void getRetweetsOfMe(){

    }
}
