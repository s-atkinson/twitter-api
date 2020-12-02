import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.AddRule;
import pojo.AddRuleResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

//import org.junit.Assert;
//import org.junit.Test;


public class PostTweets {

    RestAssured ra = new RestAssured();
    Payload payload = new Payload();

    RequestSpecification requestStatus = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/").setBasePath("1.1/statuses/")
            .build().auth().oauth(Keys.CONSUMER_KEY, Keys.CONSUMER_SECRET, Keys.ACCESS_TOKEN, Keys.TOKEN_SECRET);
    RequestSpecification requestStream = new RequestSpecBuilder().setBaseUri("https://api.twitter.com/").setBasePath("2/tweets/")
            .build().header("Authorization", Keys.BERARER_TOKEN).header("Content-type", "application/json");

    Random random = new Random();

    @Test
    public void createTweet() {
        String tweetTag = String.format("%04d", random.nextInt(10000));
        Response response = given().spec(requestStatus).log().all().queryParam("status", "API Test Tweet #" + tweetTag).when().post("update.json").then().log().all().statusCode(200).extract().response();
        String rb = response.getBody().asString();
        Assert.assertTrue(rb.contains("API Test"));
        System.out.println(rb);
    }

    @Test(dataProvider = "status")
    public void bodyTweet(String tweet) {

        given().log().all().spec(requestStatus).queryParam("status", tweet).when().post("update.json").then().log().all().statusCode(200).extract().response();
    }

    @DataProvider(name = "status")
    public Object[][] getStatus() {
        String tweetTag = String.format("%04d", random.nextInt(10000));
        return new Object[][]{
                {"API Test Tweet #1 - " + tweetTag}, {"API Test Tweet #2 - " + tweetTag}
        };
    }

    @Test
    //add rules must use bearer token
    //add rules must contain contain content type in header
    public void addStaticRules() throws IOException {
        given().spec(requestStream).log().all().body(new String(Files.readAllBytes(Paths.get("/Users/atkins95/workspace/twitter-api/src/main/resources/addRules.json")))).when()
                .post("search/stream/rules").then().log().all().statusCode(201).extract().response();
    }

    @Test
    public void addDynamicRules() {
        String response = given().spec(requestStream).log().all().body(payload.addRules("rat", "bag")).when().post("search/stream/rules").then().log().all()
                .statusCode(201).assertThat().body("data[0].value", equalTo("rat"))
                .extract().response().asString();

        JsonPath validateFields = new JsonPath(response);
        String val = validateFields.get("data[0].value");
        Assert.assertEquals(val, "rat");
    }

    @Test
    public void addPojoRule(){
        List<AddRule.Add> rule = new ArrayList<>();
        rule.add(0,new AddRule.Add("meow","sound"));
        rule.add(1,new AddRule.Add("meow2","sound2"));
        AddRule addRule = new AddRule(rule);

        AddRuleResponse arr = given().spec(requestStream).log().all().body(addRule).when().post("search/stream/rules").then().log().all()
                .statusCode(201).extract().response().as(AddRuleResponse.class);

        String value = arr.getData().get(0).getValue().toString();
        String tag = arr.getData().get(0).getTag().toString();
        String value2 = arr.getData().get(1).getValue().toString();
        String tag2 = arr.getData().get(1).getTag().toString();
        String created = arr.getMeta().getSummary().getCreated().toString();

        Assert.assertEquals(value,"meow");
        Assert.assertEquals(tag,"sound");
        Assert.assertEquals(value2,"meow2");
        Assert.assertEquals(tag2,"sound2");
        Assert.assertEquals(created,"2");
    }

    @Test
    public void getRules() {
        given().spec(requestStream).log().all().when().get("search/stream/rules").then().log().all().statusCode(200);
    }

    @Test
    public void deleteAllRules() {
        String response = given().spec(requestStream).log().all().when().get("search/stream/rules").then().log().all().statusCode(200).extract().response().asString();
        if (response.contains("data")) {
            JsonPath jp = new JsonPath(response);
            int count = jp.getInt("data.size()");
            for (int i = 0; i < count; i++) {
                String id = jp.get("data[" + i + "].id");
                System.out.println(id);

                given().spec(requestStream).log().all().body(payload.deleteRules(id)).when().post("search/stream/rules").then().log().all().statusCode(200);
                System.out.println("Deleted id: " + id);
            }
        } else System.out.println("No more rules to delete");
    }

    @Test
    public void connectStream(){

    }
}
