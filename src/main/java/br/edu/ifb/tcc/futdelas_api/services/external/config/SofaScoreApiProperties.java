package br.edu.ifb.tcc.futdelas_api.services.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.api")
public class SofaScoreApiProperties {
    private String baseUrl = "https://sofascore.p.rapidapi.com";
    private String apiHost = "sofascore.p.rapidapi.com";
    private String apiKey;

    public String getApiKey() {
        return System.getenv("EXTERNAL_API_KEY") != null ? 
               System.getenv("EXTERNAL_API_KEY") : this.apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiHost() {
        return apiHost;
    }
}