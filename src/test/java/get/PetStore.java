package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;

import java.util.List;
import java.util.Map;

public class PetStore {


    @Test
    public void getPetTest() {

        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://petstore.swagger.io/v2/pet/10567")
                .then().statusCode(200).extract().response();


        //deserialization:

        Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        Assert.assertEquals("hatiko", deserializedResponse.get("name"));
        Assert.assertEquals(10567, deserializedResponse.get("id"));
        Assert.assertEquals("sdet doggie", deserializedResponse.get("status"));

        Object categoryObject = deserializedResponse.get("category");

        Map<String, Object> category = (Map<String, Object>) categoryObject;

        System.out.println("category: " + category);


        List<String> photoUrlsList = (List<String>) deserializedResponse.get("photoUrls");
        System.out.println("Photo URLs: " + photoUrlsList);


    }


    @Test
    public void petPojoTest() {
        Response response = RestAssured.given().accept("application/json")
                .when().get("https://petstore.swagger.io/v2/pet/10567")
                .then().statusCode(200).extract().response();

        //deserialization
        PetPojo deserializedResponse = response.as(PetPojo.class);

        Assert.assertEquals("hatiko", deserializedResponse.getName());
        Assert.assertEquals(10567, deserializedResponse.getId());




    }


}
