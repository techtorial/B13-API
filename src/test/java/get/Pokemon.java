package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PokemonPojo;

import java.util.List;
import java.util.Map;

public class Pokemon {

    /**
     * Construct request:
     * 1. define URL
     * 2. add header
     * 3. define the HTTP Method
     * Send request
     * Validate response status code
     */

    @Test
    public void getPokemonTest() {

        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://pokeapi.co/api/v2/pokemon")
                .then().statusCode(200).extract().response();

        Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

//        deserializedResponse.put("next", "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20");
//        deserializedResponse.put("previous", null);

//        deserializedResponse.get("count");

        System.out.println(deserializedResponse);

        Assert.assertEquals(null, deserializedResponse.get("previous"));
        Assert.assertNull(deserializedResponse.get("previous"));


        //
        Object object = deserializedResponse.get("results"); // => get() return type is Object, bc Map<String, Object> deserializedResponse
        List<Map<String, String>> list = (List<Map<String, String>>) object; //casting object to List<Map<String, String>>


        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            String name = map.get("name");
            System.out.println(name);
            /*
            {
            "name": "bulbasaur",
            "url": "https://pokeapi.co/api/v2/pokemon/1/"
            }
             */

        }

    }


    @Test
    public void pokemonPojoTest() {

        Response response = RestAssured.given().accept("application/json")
                .when().get("https://pokeapi.co/api/v2/pokemon")
                .then().statusCode(200).extract().response();

        PokemonPojo parsedResponse = response.as(PokemonPojo.class);

        Assert.assertEquals(1154, parsedResponse.getCount());
        Assert.assertEquals("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", parsedResponse.getNext());
        Assert.assertEquals(20, parsedResponse.getResults().size());

    }


}
