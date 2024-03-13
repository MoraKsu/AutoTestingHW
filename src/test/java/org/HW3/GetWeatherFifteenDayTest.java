package org.HW3;

import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
public class GetWeatherFifteenDayTest extends AccuweatherAbstractTest {
    @Test
    void get_Fifteen_day_return_401() {

        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("location", 295302)
                .when()
                .request(Method.GET,getBaseUrl()+"/forecasts/{version}/daily/15day/{location}")
                .then()
                .statusCode(401);
    }
}
