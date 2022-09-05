package br.com.cvc.evaluation.config;

import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class WebClientTestConfig {
    @Bean
    public WebClient webClient(@Autowired  final ClientAndServer mockServer) {
        final var baseUrl = "http://127.0.0.1:"
                        .concat(mockServer.getLocalPort().toString());

        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
