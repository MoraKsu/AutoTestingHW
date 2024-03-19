package org.seminar.spoon;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.HW5.spoon.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = "Тестирование с помощью WireMock и логгирование")
@Feature(value = "Работа на Семинаре 5")
public class ClassifyCuisineTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(ClassifyCuisineTest.class);


    @Override
    public String convertResponseToString(HttpResponse response) throws IOException {
        return super.convertResponseToString(response);
    }

    @Test
    @DisplayName("ClassifyCuisineTest")
    @Description("POST ClassifyCuisine")
    @Link("")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Эрина Ксения")
    @Story(value = "Тестирование метода post_shouldReturn200")
    void post_shouldReturn200() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        ClassifyCuisineDTO bodyResponse = new ClassifyCuisineDTO();
        bodyResponse.setCuisine("CuisineValue");

        logger.debug("Формирование мока для POST /recipes/cuisine");
        stubFor(post(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withRequestBody(containing("\"title\": \"Pork roast with green beans\""))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyResponse))));

        stubFor(post(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"title\": \"lkjhcfghjkl52145\""))
                .willReturn(aResponse()
                        .withStatus(202).withBody(mapper.writeValueAsString(bodyResponse))));


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(getBaseUrl()+"/recipes/cuisine");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity("{" +
                "\"title\": \"Pork roast with green beans\"" +
                "}"));

        HttpPost requestOk = new HttpPost(getBaseUrl()+"/recipes/cuisine");
        requestOk.addHeader("Content-Type", "application/json");
        requestOk.setEntity(new StringEntity("{" +
                "\"title\": \"lkjhcfghjkl52145\"" +
                "}"));
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);

        HttpResponse responseOk = httpClient.execute(requestOk);
        //then
        verify(postRequestedFor(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));

        verify(postRequestedFor(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/json")));

        verify(2,postRequestedFor(urlEqualTo("/recipes/cuisine")));

        assertEquals(200, response.getStatusLine().getStatusCode());
        ClassifyCuisineDTO bodyToCheck = mapper.readValue(response.getEntity().getContent(), ClassifyCuisineDTO.class);
        assertEquals("CuisineValue", bodyToCheck.getCuisine());

        assertEquals(202, responseOk.getStatusLine().getStatusCode());
        bodyToCheck = mapper.readValue(responseOk.getEntity().getContent(), ClassifyCuisineDTO.class);
        assertEquals("CuisineValue", bodyToCheck.getCuisine());
    }

    @Test
    @DisplayName("ClassifyCuisineTest")
    @Description("POST ClassifyCuisine Error")
    @Link("")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Эрина Ксения")
    @Story(value = "Тестирование метода post_shouldReturn403")
    void post_shouldReturn403() throws IOException {
        logger.info("Тест код ответ 403 запущен");
        //given
        logger.debug("Формирование мока для POST /recipes/cuisine");
        stubFor(post(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"title\": \"Pork roast with green beans\""))
                .willReturn(aResponse()
                        .withStatus(403).withBody("ERROR")));


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(getBaseUrl()+"/recipes/cuisine");
        request.addHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity("{" +
                "\"title\": \"Pork roast with green beans\"" +
                "}"));
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(postRequestedFor(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("application/json")));
        assertEquals(403, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }

}
