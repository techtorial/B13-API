package post;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class Pet {


    @Test
    public void createPetTest() {

        //https://petstore.swagger.io/v2/pet
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .body(PayloadUtils.getPetPayload())
                .when().post()
                .then().statusCode(200).extract().response();


        PetPojo deserializedResponse = response.as(PetPojo.class);
        Assert.assertEquals("hatiko", deserializedResponse.getName());


        //https://petstore.swagger.io/v2/pet/1056789
        response = RestAssured.given().accept("application/json")
                .when().get("/1056789")
                .then().statusCode(200).extract().response();
    }





}
