# Simple Spring Boot Project

### Architecture
- Java 17
- Spring Boot 3.0.0-M4
- [Lombok](https://projectlombok.org) to avoid boilerplate code
- [Mapstruct](https://mapstruct.org) for conversions between domains
- [WebClient](https://www.baeldung.com/spring-5-webclient): a reactive client HTTP
- [SL4J](https://www.slf4j.org/manual.html) for logging
- [io.jsonwebtoken](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api) for JWT authentication
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide) for unit tests
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Fixture Factory](https://github.com/six2six/fixture-factory) for generate fake objects
- [Hamcrest](http://hamcrest.org/JavaHamcrest) for alternative asserts
- [EasyRandom](https://github.com/j-easy/easy-random) for generate randomic objects
- [Wiremock](https://wiremock.org/docs/) for asserts with endpoints and http clients

### Instructions
- Clone the project
- Run the project: `$ mvn spring-boot:run`

### About the application
It's a RESTFull API for booking and searching a hotel with JWT authentication.

### JWT Authentication
```shell
curl --request POST \
--url http://localhost:8080/auth \
--header 'Content-Type: application/json' \
--data '{
"user": "usuario",
"passwd": "senha"
}'
```

### Access the application with the token
````shell
curl --request GET \
  --url http://localhost:8080/booking/55/2022-08-27/2022-08-27/2/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwiaXNzIjoidGVzdGVjdmMiLCJpYXQiOjE2NjE2NTM4MzMsImV4cCI6MTY2MTc0MDIzM30.daC15QAqKtpJRhSz-Y63Ul_bHOVZHCpJeZbZnx7JMjI'
````

````shell
curl --request GET \
  --url http://localhost:8080/hotel/6 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwiaXNzIjoidGVzdGVjdmMiLCJpYXQiOjE2NjE2NTM4MzMsImV4cCI6MTY2MTc0MDIzM30.daC15QAqKtpJRhSz-Y63Ul_bHOVZHCpJeZbZnx7JMjI'
````

### References
- [Baeldung](https://www.baeldung.com)
- [FreeCodeCamp](https://www.freecodecamp.org/portuguese/news/como-configurar-a-autenticacao-e-a-autorizacao-no-jwt-para-o-spring-boot-em-java/)

