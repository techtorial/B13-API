package gorest.utils;

import gorest.pojos.GoRestPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ReusableMethods {

    final static String APPLICATION_JSON = "application/json";

    public static Response createUser(){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users";

        File createUserBody = new File("src/test/java/gorest/files/createUser.json");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .header("Accept", APPLICATION_JSON)
                .header("Content-Type", APPLICATION_JSON)
                .body(createUserBody)
                .when().post()
                .then().statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("code", Matchers.equalTo(201))
                .extract().response();
        System.out.println(response.getBody().prettyPrint());
        return response;
    }

    public static Response getUser(String userId){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users/"+userId;

        Response response = RestAssured.given()
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .when().get()
                .then().statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("code", Matchers.equalTo(200))
                .extract().response();
        System.out.println(response.getBody().prettyPrint());
        return response;
    }

    public static Response updateUser(String userId){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users/"+userId;

        File updateBodyFile = new File("src/test/java/gorest/files/updateBody.json");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .header("Accept", APPLICATION_JSON)
                .header("Content-Type", APPLICATION_JSON)
                .body(updateBodyFile)
                .when().put()
                .then().statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("code", Matchers.equalTo(200))
                .extract().response();
        System.out.println(response.getBody().prettyPrint());
        return response;
    }


    @Test
    public void endToEndTest(){
        // deserialize createUser() response
        GoRestPojo createUserResponse = createUser().as(GoRestPojo.class);
        // get the id from create user response
        Integer createUserId = (Integer)createUserResponse.getData().get("id");
        // deserialize getUser() response
        GoRestPojo getUserResponse = getUser(String.valueOf(createUserId)).as(GoRestPojo.class);
        // get the id from getUser response
        Integer getUserId = (Integer) getUserResponse.getData().get("id");
        // verify that the id from create user is the same as the id from get user
        Assert.assertEquals(createUserId,getUserId);
        // update the user from status active to inactive
        Response updateUserMethodResponse = updateUser("" + createUserId);
        //  deserialize the body
        GoRestPojo updateUserResponse = updateUserMethodResponse.as(GoRestPojo.class);
        // get the status from update method
        String updateUserStatus = (String)updateUserResponse.getData().get("status");
        // get the user that we just updated and deserialize it
        GoRestPojo getUserResponse2 = getUser(""+createUserId).as(GoRestPojo.class);
        // verify the status of the user is inactive
        Assert.assertEquals(updateUserStatus,getUserResponse2.getData().get("status"));

    }


}
