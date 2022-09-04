package br.com.cvc.evaluation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class WebClientConfig {
    @Bean
    public WebClient webClient(@Autowired  final WireMockServer wireMockServer) {
        return WebClient.builder().baseUrl(wireMockServer.baseUrl()).build();
    }
}
