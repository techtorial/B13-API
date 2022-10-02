package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import utils.PayloadUtils;

public class Slack {


    @Test
    public void sendMessageTest() {

        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getSlackPayload("Temirlan: message from Java"))
                .header("Authorization", "Bearer ")
                .when().post()
                .then().statusCode(200).log().all().extract().response();

    }
}
