package post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.SlackPojo;
import utils.PayloadUtils;

import java.io.File;
import java.io.IOException;

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



    @Test
    public void slackMessageTest() throws IOException {
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";


        SlackPojo slackMessagePojo = new SlackPojo();
        slackMessagePojo.setChannel("C044QH2SS3U");
        slackMessagePojo.setText("Temirlan: message from Pojo");


        File file = new File("src/test/resources/slackMessage.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, slackMessagePojo);

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(slackMessagePojo)
                .header("Authorization",
                        "Bearer xoxb-3471786148807-4185093481968-fmU4qlRMTMEOrY1A5jOxNgD9")
                .when().post()
                .then().statusCode(200)
                .body("ok", Matchers.equalTo(true))
                .extract().response();

    }

    @Test
    public void sendSlackMessage(){
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        File file = new File("src/test/resources/slackMessage.json");
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization",
                        "Bearer xoxb-3471786148807-4185093481968-fmU4qlRMTMEOrY1A5jOxNgD9")
                .body(file)
                .when().post()
                .then().statusCode(200)
                .and()
                .body("ok", Matchers.is(true));
    }





}
