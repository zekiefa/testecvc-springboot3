package br.com.cvc.evaluation.endpoint;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                classes = {MockServerConfig.class, WebClientTestConfig.class})
public class BookingEndpointTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @LocalServerPort private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void testFind() {
        // Asserts
        final var tokenBuilder = new TokenBuilder();
        final var cityCode = 55;
        final var checkin = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        final var checkout = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        // Act | Asserts
        given()
                        .headers("Authorization", "Bearer " + tokenBuilder.createJWT("usuario", 60000L))
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("/booking/%d/%s/%s/1/1", cityCode, checkin, checkout))
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Hotel[].class);
    }

    @Test
    void testFindWithoutToken() {
        // Act | Asserts
        given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .log().all()
                        .when()
                        .get("/booking/1/2/3/1/1")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.FORBIDDEN.value());
    }

}
