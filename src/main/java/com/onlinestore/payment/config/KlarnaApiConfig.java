package com.onlinestore.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class KlarnaApiConfig {
    
    @Value("${klarna.api.url}")
    private String klarnaApiUrl;
    
    @Value("${klarna.api.username}")
    private String klarnaUsername;
    
    @Value("${klarna.api.password}")
    private String klarnaPassword;
    
    @Bean
    public WebClient klarnaWebClient() {
        String authHeader = createBasicAuthHeader(klarnaUsername, klarnaPassword);
        
        return WebClient.builder()
                .baseUrl(klarnaApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authHeader)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    
    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }
}
