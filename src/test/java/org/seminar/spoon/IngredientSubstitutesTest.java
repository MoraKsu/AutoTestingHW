package org.seminar.spoon;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.HW5.spoon.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = "Тестирование с помощью WireMock и логгирование")
@Feature(value = "Работа на Семинаре 5")
public class IngredientSubstitutesTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(IngredientSubstitutesTest.class);


    @Test
    @DisplayName("IngredientSubstitutesTest")
    @Description("GET IngredientSubstitutes")
    @Link("")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Эрина Ксения")
    @Story(value = "Тестирование метода get_shouldReturn200")
    void get_shouldReturn200() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        IngredientSubstitutesDto bodyOk = new IngredientSubstitutesDto();
        bodyOk.setStatus("OK");

        IngredientSubstitutesDto bodyError = new IngredientSubstitutesDto();
        bodyError.setStatus("Error");

        logger.debug("Формирование мока для GET /food/ingredients/substitutes");
        stubFor(any(urlPathEqualTo("/food/ingredients/substitutes"))
                .withQueryParam("ingredientName", equalTo("butter"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyOk))));

        stubFor(request("POST",urlPathEqualTo("/food/ingredients/substitutes"))
                .withQueryParam("ingredientName", equalTo("error"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyError))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when

        HttpPost request = new HttpPost(getBaseUrl()+"/food/ingredients/substitutes");
        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "butter")
                .build();
        request.setURI(uriOk);
        HttpResponse responseOk = httpClient.execute(request);

        URI uriError = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "error")
                .build();
        request.setURI(uriError);

        HttpResponse responseError = httpClient.execute(request);

        //then

        verify(2, postRequestedFor(urlPathEqualTo("/food/ingredients/substitutes")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals(200, responseError.getStatusLine().getStatusCode());
        assertEquals("OK", mapper.readValue(responseOk.getEntity().getContent(), IngredientSubstitutesDto.class).getStatus());
        assertEquals("Error", mapper.readValue(responseError.getEntity().getContent(), IngredientSubstitutesDto.class).getStatus());
    }

    @Test
    @DisplayName("IngredientSubstitutesTest")
    @Description("GET IngredientSubstitutes Error")
    @Link("")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Эрина Ксения")
    @Story(value = "Тестирование метода get_shouldReturn401")
    void get_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 401 запущен");
        //given
        logger.debug("Формирование мока для GET /food/ingredients/substitutes");
        stubFor(request("GET",urlPathEqualTo("/food/ingredients/substitutes"))
                .withQueryParam("apiKey", notMatching("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("ERROR")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/food/ingredients/substitutes");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "A_82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/food/ingredients/substitutes")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));

    }
}
