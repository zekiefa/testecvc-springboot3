package br.com.cvc.evaluation.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import br.com.cvc.evaluation.config.MockServerConfig;
import br.com.cvc.evaluation.config.WebClientTestConfig;
import br.com.cvc.evaluation.domain.Login;
import br.com.cvc.evaluation.domain.Token;
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
public class AuthenticationEndpointTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUpPort() {
        RestAssured.port = this.port;
    }

    @Test
    void testLogin() {
        // Arrange
        final var login = new Login("usuario", UUID.randomUUID().toString());

        // Act
        final var response = given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(login)
                        .log().all()
                        .when().post("/auth")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Token.class);

        // Asserts
        assertAll("success",
                        () -> assertNotNull(response),
                        () -> assertThat(response.token(), notNullValue()),
                        () -> assertThat(response.type(), is("Bearer"))
        );
    }

    @Test
    void testLoginWithUserNotExists() {
        // Arrange
        final var login = new Login("anotherUser", UUID.randomUUID().toString());

        // Act | Assert
        final var response = given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(login)
                        .log().all()
                        .when().post("/auth")
                        .then()
                        .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
