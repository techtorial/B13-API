package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StarWars {

    //GET https://swapi.dev/api/people
    //Validate status code
    //Deserialize the response
    //Print out all character names from page 1


    @Test
    public void starWarsTest() {


        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people")
                .then().statusCode(200).extract().response();

        //deserialization
        Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });
        Object results = parsedResponse.get("results");// -> returns Object

        List<Map<String, Object>> list = (List<Map<String, Object>>) results;

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            System.out.println(map.get("name"));
        }
    }
}
