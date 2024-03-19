package org.HW5.spoon;

import io.qameta.allure.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.seminar.spoon.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = "Тестирование с помощью WireMock и логгирование")
@Feature(value = "Работа на Семинаре 5")
public class SimilarRecipesTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(SimilarRecipesTest.class);

    @Test
    @DisplayName("SimilarRecipesTest")
    @Description("GET SimilarRecipes Error")
    @Link("")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Эрина Ксения")
    @Story(value = "Тестирование метода get_shouldReturn500")
    void get_shouldReturn500() throws IOException {
        logger.info("Тест код ответ 500 запущен");
        //given
        logger.debug("Формирование мока для GET /recipes/715538/similar");
        stubFor(get(urlPathEqualTo("/recipes/715538/similar"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/recipes/715538/similar");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/recipes/715538/similar")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }

}