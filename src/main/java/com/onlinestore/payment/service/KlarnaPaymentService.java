package com.onlinestore.payment.service;

import com.onlinestore.payment.model.CreateOrderRequest;
import com.onlinestore.payment.model.CustomerTokenCreationRequest;
import com.onlinestore.payment.model.CustomerTokenCreationResponse;
import com.onlinestore.payment.model.MerchantSession;
import com.onlinestore.payment.model.Order;
import com.onlinestore.payment.model.Session;

public interface KlarnaPaymentService {
    
    /**
     * Create a new payment session
     * 
     * @param session The session data
     * @return The created merchant session
     */
    MerchantSession createSession(Session session);
    
    /**
     * Get details about a session
     * 
     * @param sessionId The session ID
     * @return The session details
     */
    Session getSession(String sessionId);
    
    /**
     * Update a session
     * 
     * @param sessionId The session ID
     * @param session The updated session data
     */
    void updateSession(String sessionId, Session session);
    
    /**
     * Create an order
     * 
     * @param authorizationToken The authorization token
     * @param orderRequest The order request data
     * @return The created order
     */
    Order createOrder(String authorizationToken, CreateOrderRequest orderRequest);
    
    /**
     * Cancel an authorization
     * 
     * @param authorizationToken The authorization token
     */
    void cancelAuthorization(String authorizationToken);
    
    /**
     * Generate a customer token
     * 
     * @param authorizationToken The authorization token
     * @param tokenRequest The token request data
     * @return The customer token creation response
     */
    CustomerTokenCreationResponse generateCustomerToken(String authorizationToken, CustomerTokenCreationRequest tokenRequest);
}
