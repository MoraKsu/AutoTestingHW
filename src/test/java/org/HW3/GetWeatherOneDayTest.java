package org.HW3;

import org.HW3.accuweather.weather.DailyForecast;
import org.HW3.accuweather.weather.Weather;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.given;
public class GetWeatherOneDayTest extends AccuweatherAbstractTest {
    @Test
    void getWeatherOneDay_shouldReturn() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295302")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(1,response.getDailyForecasts().size());
        Assertions.assertNotNull(response.getHeadline());
    }

    @Test
    void getDailyForecastsList() {

        List<DailyForecast> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295302")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().jsonPath().getList("DailyForecasts", DailyForecast.class);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    void getString() {
        String response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295302")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract().asString();
        Assertions.assertTrue(response.contains("Headline"));
        Assertions.assertTrue(response.contains("DailyForecasts"));
    }

    @Test
    void invalidApiKey_shouldReturnUnauthorized() {
        given()
                .queryParam("apikey", "invalid_api_key")
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/295302")
                .then()
                .statusCode(401);
    }

    @Test
    void invalidLocation_shouldReturnNotFound() {
        given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/invalid_location")
                .then()
                .statusCode(400);
    }

    @Test
    void missingApiKey_shouldReturnUnauthorized() {
        given()
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/295302")
                .then()
                .statusCode(401);
    }
}
