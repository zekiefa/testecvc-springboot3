package br.com.cvc.evaluation.config;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockServerConfig {
    private static final String HOTELS_JSON_FILE = "/hotels.json";
    private static final String BASE_PATH = "/hotels";

    @Bean
    public ClientAndServer mockServer() {
        final var clientAndServer = startClientAndServer();
        this.stubHotels(clientAndServer);

        return clientAndServer;
    }

    private void stubHotels(final ClientAndServer clientAndServer) {
        try (InputStream is = MockServerConfig.class.getResourceAsStream(HOTELS_JSON_FILE)) {
            assert is != null;
            final var hotels = new String(is.readAllBytes());
            // Stub for full list of hotels:
            clientAndServer.when(
                            request()
                                            .withMethod("GET")
                                            .withPath(BASE_PATH.concat("/avail/{hotelId}"))
                                            .withPathParameters(
                                                            param("hotelId", "([0-9]*)")
                                            )
            ).respond(
                            response().withBody(json(hotels, MatchType.STRICT))
            );
            // Stub for each hotel
            try (StringReader sr = new StringReader(hotels); final var parser = Json.createParser(sr)) {
                parser.next();
                for (JsonValue hotel : parser.getArray()) {
                    final var id = hotel.asJsonObject().getInt("id");
                    clientAndServer.when(
                                    request()
                                                    .withMethod("GET")
                                                    .withPath(BASE_PATH.concat("/{hotelId}"))
                                                    .withPathParameters(
                                                                    param("hotelId", String.format("%d", id))
                                                    )
                    ).respond(
                                    response().withBody(
                                                                    json(hotel.toString(), MatchType.STRICT)
                                    )
                    );
                }
            }
        } catch (IOException e) {
            fail("Could not configure Mock server. Caused by: " + e.getMessage());
        }
    }
}
