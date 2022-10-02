package delete;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.PetDeletePojo;
import utils.PayloadUtils;

import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Pet {


    @Before
    public void setup() {
        baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
    }

    @Test
    public void deletePetTest() {

        //post request
        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload())
                .when().post()
                .then().statusCode(200)
                .extract().response();


        //TODO add post response validation - validate name, status, id


        //get pet request
        response = given().accept(ContentType.JSON)
                .when().get("/10567891245")
                .then().statusCode(200).extract().response();

        //TODO add get response validation - validate name, status, id


        //delete pet request
        response = given().accept(ContentType.JSON)
                .when().delete("/10567891245")
                .then().statusCode(200).extract().response();

        PetDeletePojo parsedDeletePetResponse = response.as(PetDeletePojo.class);

        Assert.assertEquals(200, parsedDeletePetResponse.getCode());


        response = given().accept(ContentType.JSON)
                .when().get("/10567891245")
                .then().statusCode(400).extract().response();


        Map<String, Object> deserializedGetResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        String expectedMessage = "Pet not found";
//        String actualMessage = deserializedGetResponse.get("message").toString();
        String actualMessage = String.valueOf(deserializedGetResponse.get("message"));
//        String actualMessage = (String)deserializedGetResponse.get("message");
        Assert.assertEquals(expectedMessage, actualMessage);



    }


}
