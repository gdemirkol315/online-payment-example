package com.onlinestore.payment.service.impl;

import com.onlinestore.payment.exception.KlarnaApiException;
import com.onlinestore.payment.model.CreateOrderRequest;
import com.onlinestore.payment.model.CustomerTokenCreationRequest;
import com.onlinestore.payment.model.CustomerTokenCreationResponse;
import com.onlinestore.payment.model.ErrorV2;
import com.onlinestore.payment.model.MerchantSession;
import com.onlinestore.payment.model.Order;
import com.onlinestore.payment.model.Session;
import com.onlinestore.payment.service.KlarnaPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KlarnaPaymentServiceImpl implements KlarnaPaymentService {
    
    private final WebClient klarnaWebClient;
    
    @Override
    public MerchantSession createSession(Session session) {
        try {
            return klarnaWebClient.post()
                    .uri("/payments/v1/sessions")
                    .bodyValue(session)
                    .retrieve()
                    .bodyToMono(MerchantSession.class)
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to create session");
            return null; // This line will never be reached due to exception handling
        }
    }
    
    @Override
    public Session getSession(String sessionId) {
        try {
            return klarnaWebClient.get()
                    .uri("/payments/v1/sessions/{sessionId}", sessionId)
                    .retrieve()
                    .bodyToMono(Session.class)
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to get session: " + sessionId);
            return null; // This line will never be reached due to exception handling
        }
    }
    
    @Override
    public void updateSession(String sessionId, Session session) {
        try {
            klarnaWebClient.post()
                    .uri("/payments/v1/sessions/{sessionId}", sessionId)
                    .bodyValue(session)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to update session: " + sessionId);
        }
    }
    
    @Override
    public Order createOrder(String authorizationToken, CreateOrderRequest orderRequest) {
        try {
            return klarnaWebClient.post()
                    .uri("/payments/v1/authorizations/{authorizationToken}/order", authorizationToken)
                    .bodyValue(orderRequest)
                    .retrieve()
                    .bodyToMono(Order.class)
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to create order with authorization token: " + authorizationToken);
            return null; // This line will never be reached due to exception handling
        }
    }
    
    @Override
    public void cancelAuthorization(String authorizationToken) {
        try {
            klarnaWebClient.delete()
                    .uri("/payments/v1/authorizations/{authorizationToken}", authorizationToken)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to cancel authorization: " + authorizationToken);
        }
    }
    
    @Override
    public CustomerTokenCreationResponse generateCustomerToken(String authorizationToken, CustomerTokenCreationRequest tokenRequest) {
        try {
            return klarnaWebClient.post()
                    .uri("/payments/v1/authorizations/{authorizationToken}/customer-token", authorizationToken)
                    .bodyValue(tokenRequest)
                    .retrieve()
                    .bodyToMono(CustomerTokenCreationResponse.class)
                    .block();
        } catch (WebClientResponseException ex) {
            handleWebClientException(ex, "Failed to generate customer token with authorization token: " + authorizationToken);
            return null; // This line will never be reached due to exception handling
        }
    }
    
    private void handleWebClientException(WebClientResponseException ex, String message) {
        log.error("Klarna API error: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
        
        ErrorV2 error = null;
        try {
            error = ex.getResponseBodyAs(ErrorV2.class);
        } catch (Exception e) {
            log.warn("Could not parse error response", e);
        }
        
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        
        if (error != null) {
            throw new KlarnaApiException(message, status, error);
        } else {
            throw new KlarnaApiException(message + ": " + ex.getResponseBodyAsString(), status);
        }
    }
}
