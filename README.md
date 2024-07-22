# Simple Spring Boot Project

### Architecture
- Java ~~17~~ 21
- Spring Boot ~~3.0.5~~ 3.3.2
- ~~[Lombok](https://projectlombok.org) to avoid boilerplate code~~
- ~~[Mapstruct](https://mapstruct.org) for conversions between domains~~
- [WebClient](https://www.baeldung.com/spring-5-webclient): a reactive client HTTP
- [SL4J](https://www.slf4j.org/manual.html) for logging
- [io.jsonwebtoken](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api) for JWT authentication
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide) for unit tests
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Hamcrest](http://hamcrest.org/JavaHamcrest) for alternative asserts
- [EasyRandom](https://github.com/j-easy/easy-random) for generate randomic objects
- [MockServer](https://www.mock-server.com/) for asserts with endpoints and http clients

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
- [Preparing for Spring Boot 3.0](https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0)
- [Spring Boot 3 and Spring Framework 6.0 – What’s New](https://www.baeldung.com/spring-boot-3-spring-6-new)
- [New Features in Java 17](https://www.baeldung.com/java-17-new-features)
- [MockServer: Creating Expectations](https://www.mock-server.com/mock_server/creating_expectations.html)
