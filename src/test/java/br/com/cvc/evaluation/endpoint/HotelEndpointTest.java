package br.com.cvc.evaluation.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.cvc.evaluation.config.MockServerConfig;
import br.com.cvc.evaluation.config.WebClientTestConfig;
import br.com.cvc.evaluation.domain.Hotel;
import br.com.cvc.evaluation.fixtures.TokenBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes={MockServerConfig.class, WebClientTestConfig.class})
public class HotelEndpointTest {
    @LocalServerPort
    private int port;

    private static final String BASE_URL = "/api/v1/hotels";

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void testFind() {
        // Arranges
        final var tokenBuilder = new TokenBuilder();
        final var idHotel = 1;

        // Act
        final var response = given()
                        .headers("Authorization", "Bearer " + tokenBuilder.createJWT("usuario", 60000L))
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .log().all()
                        .when().get(String.format(BASE_URL.concat("/%d"), idHotel))
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Hotel.class);

        // Asserts
        assertAll("success",
                        () -> assertNotNull(response),
                        () -> assertThat(response.id(), is(idHotel))
        );
    }

    @Test
    void testFindWithoutToken() {
        // Act
        final var response = given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .log().all()
                        .when().get(BASE_URL.concat("/1"))
                        .then()
                        .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
