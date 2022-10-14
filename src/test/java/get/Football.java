package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Football {


    @Test
    public void footballTest() {
        // http://api.football-data.org/v2/competitions
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(200)
                .extract().response();

        //find the country code for Australia
        // and assert it equals to AUS


        JsonPath parsedResponse = response.jsonPath();

        List<Map<String, Object>> competitions = parsedResponse.getList("competitions");

        for (int i = 0; i < competitions.size(); i++) {
            Map<String, Object> countryMap = competitions.get(i);
            Map<String, Object> countryAreaMap = (Map<String, Object>) countryMap.get("area");
            if (countryAreaMap.get("name").equals("Australia")) {
                Assert.assertEquals("AUS", countryAreaMap.get("countryCode"));
            }
        }


        String countryCode =
                response.path("competitions.find { it.area.name == 'Australia' }.area.countryCode");
        Assert.assertEquals("AUS", countryCode);
    }


}
