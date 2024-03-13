package org.HW3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.HW3.accuweather.location.Location;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetLocationTest extends AccuweatherAbstractTest{

    @Test
    void getLocation_search_returnTolyatti() {

        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "\n" + "Tolyatti")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Tolyatti", response.get(0).getEnglishName());
    }

    @Test
    void getLocation_search_returnMultipleLocations() {
        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "New York")
                .when()
                .get(getBaseUrl() + "/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertTrue(response.size() > 1);
    }

    @Test
    void getLocation_search_returnEmptyList() {
        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Unknown City")
                .when()
                .get(getBaseUrl() + "/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getLocation_search_invalidApiKey() {
        given()
                .queryParam("apikey", "invalid_api_key")
                .queryParam("q", "Tolyatti")
                .when()
                .get(getBaseUrl() + "/locations/v1/cities/search")
                .then()
                .statusCode(401);
    }
}
