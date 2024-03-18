package org.HW5.accu;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetWeatherTenDayTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetWeatherOneDayTest.class);

    @Test
    void get_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 401 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/294021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/294021"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("Unauthorized")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/294021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);

        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/294021")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(response));
    }

    @Test
    void get_shouldReturn400_WithInvalidLocation() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 400 с недопустимым форматом местоположения запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/294021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/123456"))
                .willReturn(aResponse()
                        .withStatus(400).withBody("Bad Request")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/123456");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);

        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/123456")));
        assertEquals(400, response.getStatusLine().getStatusCode());
        assertEquals("Bad Request", convertResponseToString(response));
    }
}
