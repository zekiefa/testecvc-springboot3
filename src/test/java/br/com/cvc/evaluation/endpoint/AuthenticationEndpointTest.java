package br.com.cvc.evaluation.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.cvc.evaluation.config.WebClientConfig;
import br.com.cvc.evaluation.config.WireMockConfig;
import br.com.cvc.evaluation.domain.Login;
import br.com.cvc.evaluation.domain.Token;
import br.com.cvc.evaluation.fixtures.LoginFixture;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = {WireMockConfig.class, WebClientConfig.class})
public class AuthenticationEndpointTest {
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.cvc.evaluation.fixtures");
    }

    @BeforeEach
    void setUpPort() {
        RestAssured.port = this.port;
    }

    @Test
    void testLogin() {
        // Arrange
        final Login login = Fixture.from(Login.class).gimme(LoginFixture.VALID);

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
                        () -> assertThat(response.getToken(), notNullValue()),
                        () -> assertThat(response.getType(), is("Bearer"))
        );
    }

    @Test
    void testLoginWithUserNotExists() {
        // Arrange
        final Login login = Fixture.from(Login.class).gimme(LoginFixture.NOT_FOUND);

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
