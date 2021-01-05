package stepHelpers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonState {

    private static RequestSpecification reqSpec;
    private static Response response;
    private static Logger logger = LogManager.getLogger(CommonState.class);

    public static RequestSpecification getRequestSpecification(){
        return reqSpec;
    }

    public static void setRequestSpecification(RequestSpecification reqSpec){
        CommonState.reqSpec = reqSpec;
        logger.info("Setting common state for request specifications");
    }

    public static Response getResponse(){
        return response;
    }

    public static void setResponse(Response response){
        CommonState.response = response;
        logger.info("Setting common state for response body");
    }
}
