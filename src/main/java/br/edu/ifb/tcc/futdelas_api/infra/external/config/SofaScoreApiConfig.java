package br.edu.ifb.tcc.futdelas_api.infra.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SofaScoreApiConfig {

    private final SofaScoreApiProperties properties;

    public SofaScoreApiConfig(SofaScoreApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient sofascoreWebClient() {
        return WebClient.builder()
            .baseUrl(properties.getBaseUrl())
            .defaultHeader("X-RapidAPI-Key", properties.getApiKey())
            .defaultHeader("X-RapidAPI-Host", properties.getApiHost())
            .build();
    }
}