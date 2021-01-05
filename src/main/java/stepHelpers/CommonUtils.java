package stepHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;

public class CommonUtils {

    private String updatedBody = null;
    public Response response;
    private RequestSpecifications httpRequest = new RequestSpecifications();
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, String> storedValue = new HashMap<>();
    private static Logger logger = LogManager.getLogger(CommonUtils.class);

    public void getAuthorizedUser(String userType) {
        httpRequest.getHostAPI(userType);
        logger.info("Retrieved authorized " + userType + " user");
        CommonState.setRequestSpecification(httpRequest.getRequestSpec());
    }

    public void requestWithEndpoint(String requestType, RequestSpecification reqSpec, String endpoint) {
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint");
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithPathParam(String requestType, RequestSpecification reqSpec, String endpoint, String pathValue) {
        if (pathValue.startsWith("{") && pathValue.endsWith("}")) {
            pathValue = getStoredValue(pathValue);
        }
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with path parameter " + pathValue);
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithBody(String requestType, RequestSpecification reqSpec, String endpoint, String body) {
        String payload = readFile(body, ".json");
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with body " + body + ".json");
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).body(payload).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).body(payload).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).body(payload).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).body(payload).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).body(payload).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithQueryParams(String requestType, RequestSpecification reqSpec, String endpoint, String key, String value) {
        HashMap<String, String> params = (HashMap<String, String>) getParams(key, value);
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with query parameters key: " + key + " and value: " + value);
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).queryParams(params).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).queryParams(params).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).queryParams(params).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).queryParams(params).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).queryParams(params).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithPathParamQueryParams(String requestType, RequestSpecification reqSpec, String endpoint, String pathValue, String key, String value) {
        if (pathValue.startsWith("{") && pathValue.endsWith("}")) {
            pathValue = getStoredValue(pathValue);
        }
        HashMap<String, String> params = (HashMap<String, String>) getParams(key, value);
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with query parameters key: " + key + " and value: " + value);
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).queryParams(params).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).queryParams(params).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).queryParams(params).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).queryParams(params).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).pathParam("key", pathValue).queryParams(params).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithQueryParamsAndBody(String requestType, RequestSpecification reqSpec, String endpoint, String key, String value, String body) {
        HashMap<String, String> params = (HashMap<String, String>) getParams(key, value);
        String payload = readFile(body, ".json");
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with query parameters key: " + key + " value: " + value + " and body " + body + ".json");
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).queryParams(params).body(payload).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).queryParams(params).body(payload).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).queryParams(params).body(payload).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).queryParams(params).body(payload).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).queryParams(params).body(payload).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithValueInBody(String requestType, RequestSpecification reqSpec, String endpoint, String value, String body) {
        replaceBodyStrings(value, body);
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with body " + body + ".json");
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).body(updatedBody).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).body(updatedBody).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).body(updatedBody).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).body(updatedBody).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).body(updatedBody).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    public void requestWithQueryParamsAndValueInBody(String requestType, RequestSpecification reqSpec, String endpoint, String paramKey, String paramValue, String value, String body) {
        replaceBodyStrings(value, body);
        HashMap<String, String> params = (HashMap<String, String>) getParams(paramKey, paramValue);
        logger.info("Sending " + requestType + " request to the " + endpoint + " endpoint with body " + body + ".json");
        if (requestType.equalsIgnoreCase("GET")) {
            response = given().spec(reqSpec).queryParams(params).body(updatedBody).when().get(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("POST")) {
            response = given().spec(reqSpec).queryParams(params).body(updatedBody).when().post(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("PUT")) {
            response = given().spec(reqSpec).queryParams(params).body(updatedBody).when().put(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("DELETE")) {
            response = given().spec(reqSpec).queryParams(params).body(updatedBody).when().delete(endpoint).then().log().all().extract().response();
        } else if (requestType.equalsIgnoreCase("OPTIONS")) {
            response = given().spec(reqSpec).queryParams(params).body(updatedBody).when().options(endpoint).then().log().all().extract().response();
        }
        CommonState.setResponse(response);
    }

    private void replaceBodyStrings(String value, String body) {
        String payload = readFile(body, ".json");
        StringBuilder sb = new StringBuilder(payload);
        if (value.contains(",")) {
            String[] values = value.split(",");
            int i = 1;
            for (String s : values) {
                String placeholder = "{var" + i + "}";
                if (s.startsWith("{") && s.endsWith("}")) {
                    s = getStoredValue(s);
                }
                updatedBody = updateBodyWithValue(sb, placeholder, s);
                logger.info("Body updated with value " + s + " in place of " + placeholder);
                i = i + 1;
            }
        } else {
            String placeholder = "{var1}";
            if (value.startsWith("{") && value.endsWith("}")) {
                value = getStoredValue(value);
            }
            updatedBody = updateBodyWithValue(sb, placeholder, value);
            logger.info("Body updated with value " + value + " in place of " + placeholder);
        }
    }

    String updateBodyWithValue(StringBuilder sb, String placeholder, String value) {
        int index = sb.indexOf(placeholder);
        while (index != -1) {
            sb.replace(index, index + placeholder.length(), value);
            index += value.length();
            index = sb.indexOf(placeholder, index);
        }
        return sb.toString();
    }

    private Map<String, String> getParams(String key, String value) {
        Map<String, String> params = new HashMap<>();
        String[] keys = key.split("&&");
        String[] values = value.split("&&");
        if (keys.length == 1 && values.length == 1) {
            if (values[0].startsWith("{") && values[0].endsWith("}")) {
                values[0] = getStoredValue(values[0]);
            }
            params.put(keys[0], values[0]);
        } else if (keys.length == 2 && values.length == 2) {
            if (values[0].startsWith("{") && values[0].endsWith("}")) {
                values[0] = getStoredValue(values[0]);
            }
            if (values[1].startsWith("{") && values[1].endsWith("}")) {
                values[1] = getStoredValue(values[1]);
            }
            params.put(keys[0], values[0]);
            params.put(keys[1], values[1]);
        } else if (keys.length == 3 && values.length == 3) {
            if (values[0].startsWith("{") && values[0].endsWith("}")) {
                values[0] = getStoredValue(values[0]);
            }
            if (values[1].startsWith("{") && values[1].endsWith("}")) {
                values[1] = getStoredValue(values[1]);
            }
            if (values[2].startsWith("{") && values[2].endsWith("}")) {
                values[2] = getStoredValue(values[2]);
            }
            params.put(keys[0], values[0]);
            params.put(keys[1], values[1]);
            params.put(keys[2], values[2]);
        } else
            throw new IllegalArgumentException("Incompatible combination of key/value pairs specified in request query parameters");
        return params;
    }

    public void verifyStatusCode(Response response, int statusCode) {
        assertEquals(statusCode, response.statusCode());
        logger.info("The response status " + statusCode + " was verified");
    }

    public void verifyStatusMessage(Response response, String statusMessage) {
        assert (response.getStatusLine().contains(statusMessage));
        logger.info("The response contained '" + statusMessage + "' in the status line");
    }

    public void verifyListSize(Response response, String list, int expectedSize) throws Exception {
        if (response.jsonPath().get(list) != null) {
            int actualSize = response.jsonPath().getList(list).size();
            assertEquals(expectedSize, actualSize);
            logger.info("The list '" + list + "' with expected size of " + expectedSize + " matches actual size of " + actualSize);
        } else throw new NoSuchFieldException("Cannot match list " + list + " to a key in the response body");
    }

    public void verifyResponseKeyValuePairs(Response response, String key, String value) {
        String responseBody = response.asString();
        JsonPath verifyPairs = new JsonPath(responseBody);
        String[] keys = key.split("&&");
        String[] values = value.split("&&");
        if (keys.length == 1 && values.length == 1) {
            assertEquals(values[0], verifyPairs.get(keys[0]).toString());
            logger.info("The response body contains key: " + key + ", with value: " + value);
        } else if (keys.length == 2 && values.length == 2) {
            assertEquals(values[0], verifyPairs.get(keys[0]).toString());
            assertEquals(values[1], verifyPairs.get(keys[1]).toString());
            logger.info("The response body contains keys: " + Arrays.toString(keys) + ", with values: " + Arrays.toString(values));
        } else if (keys.length == 3 && values.length == 3) {
            assertEquals(values[0], verifyPairs.get(keys[0]).toString());
            assertEquals(values[1], verifyPairs.get(keys[1]).toString());
            assertEquals(values[2], verifyPairs.get(keys[2]).toString());
            logger.info("The response body contains keys: " + Arrays.toString(keys) + ", with values: " + Arrays.toString(values));
        } else
            throw new IllegalArgumentException("Incompatible combination of key/value pairs specified in request query parameters");
    }

    public void verifyResponseBody(Response response, String body) throws IOException {
        String actualBody = response.asString();
        String expectedBody = new String(Files.readAllBytes(Paths.get("src/test/resources/testData/responses/" + body + ".json")));
        JsonNode actual = mapper.readTree(actualBody);
        JsonNode expected = mapper.readTree(expectedBody);
        assertEquals(expected, actual);
        logger.info("The response body matches the " + body + " file");
    }

    public void verifyResponseBodyString(Response response, String string) {
        String responseBody = response.asString();
        Assert.assertTrue(responseBody.contains(string));
        logger.info("String '" + string + "' is contained in the response");
    }

    public void verifyResponseSchema(Response response, String schema) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("testData/schemas/" + schema + ".json"));
        logger.info(schema + ".json schema has been validated");
    }

    public void storeResponseValue(Response response, String key, String storedResponseKey) {
        String responseBody = response.asString();
        String responseValue = new JsonPath(responseBody).get(key).toString();
        storedValue.put(storedResponseKey, responseValue);
        logger.info("Stored key = " + storedResponseKey + ", Stored value = " + responseValue);
    }

    public String getStoredValue(String key) {
        String value = storedValue.get(key);
        if (value == null) {
            throw new NullPointerException("No stored value found for key " + key);
        }
        if (value.startsWith("[") && value.endsWith("]")) {
            value = value.substring(1, value.length() - 1);
        }
        logger.info("Returned stored value " + value);
        return value;
    }

    private String readFile(String fileName, String fileMask) {
        final String testDataPath = "testData/requests/" + fileName + fileMask;
        String body = null;
        try {
            body = new String(Files.readAllBytes(Paths.get("src/test/resources/" + testDataPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Returned body from the location: " + testDataPath);
        return body;
    }
}
