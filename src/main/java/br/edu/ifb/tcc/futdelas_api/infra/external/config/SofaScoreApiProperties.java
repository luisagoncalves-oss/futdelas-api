package br.edu.ifb.tcc.futdelas_api.infra.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "external.api")
@Getter @Setter
public class SofaScoreApiProperties {
    private String baseUrl = "https://sofascore.p.rapidapi.com";
    private String apiHost = "sofascore.p.rapidapi.com";
    private String apiKey;

    public String getApiKey() {
        return System.getenv("EXTERNAL_API_KEY") != null ? 
               System.getenv("EXTERNAL_API_KEY") : this.apiKey;
    }
}