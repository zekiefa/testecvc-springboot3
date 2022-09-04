package br.com.cvc.evaluation.config;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.fail;

import javax.json.Json;
import javax.json.JsonValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {
    private static final String HOTELS_JSON_FILE = "/hotels.json";
    private static final String BASE_PATH = "/hotels";

    @Bean
    public WireMockServer wireMockServer() {
        final var wireMockServer = new WireMockServer(options().dynamicPort());
        // required so we can use `baseUrl()` in the construction of `webClient` below
        wireMockServer.start();
        this.stubHotels(wireMockServer);

        return wireMockServer;
    }

    private void stubHotels(final WireMockServer wireMockServer) {
        try (InputStream is = WireMockConfig.class.getResourceAsStream(HOTELS_JSON_FILE)) {
            assert is != null;
            String hotels = new String(is.readAllBytes());

            // Stub for full list of hotels:
            wireMockServer.stubFor(get(urlPathMatching(BASE_PATH.concat("/avail/([0-9]*)")))
                            .willReturn(okJson(hotels)));

            // Stub for each hotel
            try (StringReader sr = new StringReader(hotels); final var parser = Json.createParser(sr)) {
                parser.next();
                for (JsonValue hotel : parser.getArray()) {
                    final var id = hotel.asJsonObject().getInt("id");

                    wireMockServer.stubFor(get(urlEqualTo(String.format(BASE_PATH.concat("/%d"), id)))
                                    .willReturn(okJson(hotel.toString())));
                }
            }
        } catch (IOException e) {
            fail("Could not configure Wiremock server. Caused by: " + e.getMessage());
        }
    }
}
