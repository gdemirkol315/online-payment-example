package com.onlinestore.payment.controller;

import com.onlinestore.payment.model.CreateOrderRequest;
import com.onlinestore.payment.model.CustomerTokenCreationRequest;
import com.onlinestore.payment.model.CustomerTokenCreationResponse;
import com.onlinestore.payment.model.MerchantSession;
import com.onlinestore.payment.model.Order;
import com.onlinestore.payment.model.Session;
import com.onlinestore.payment.service.KlarnaPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/klarna")
@RequiredArgsConstructor
@Tag(name = "Klarna Payment API", description = "API for integrating with Klarna Payments")
public class KlarnaPaymentController {
    
    private final KlarnaPaymentService klarnaPaymentService;
    
    @Operation(summary = "Create a payment session", description = "Creates a new Klarna payment session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session created successfully",
                    content = @Content(schema = @Schema(implementation = MerchantSession.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @PostMapping("/sessions")
    public ResponseEntity<MerchantSession> createSession(@Valid @RequestBody Session session) {
        MerchantSession merchantSession = klarnaPaymentService.createSession(session);
        return ResponseEntity.ok(merchantSession);
    }
    
    @Operation(summary = "Get session details", description = "Gets details about a Klarna payment session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Session.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<Session> getSession(
            @Parameter(description = "Session ID", required = true) @PathVariable String sessionId) {
        Session session = klarnaPaymentService.getSession(sessionId);
        return ResponseEntity.ok(session);
    }
    
    @Operation(summary = "Update a session", description = "Updates an existing Klarna payment session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Session updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @PostMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> updateSession(
            @Parameter(description = "Session ID", required = true) @PathVariable String sessionId,
            @Valid @RequestBody Session session) {
        klarnaPaymentService.updateSession(sessionId, session);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Create an order", description = "Creates a new order using an authorization token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Authorization not found"),
            @ApiResponse(responseCode = "409", description = "Data mismatch")
    })
    @PostMapping("/authorizations/{authorizationToken}/order")
    public ResponseEntity<Order> createOrder(
            @Parameter(description = "Authorization token", required = true) @PathVariable String authorizationToken,
            @Valid @RequestBody CreateOrderRequest orderRequest) {
        Order order = klarnaPaymentService.createOrder(authorizationToken, orderRequest);
        return ResponseEntity.ok(order);
    }
    
    @Operation(summary = "Cancel an authorization", description = "Cancels an existing authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Authorization cancelled successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Authorization not found")
    })
    @DeleteMapping("/authorizations/{authorizationToken}")
    public ResponseEntity<Void> cancelAuthorization(
            @Parameter(description = "Authorization token", required = true) @PathVariable String authorizationToken) {
        klarnaPaymentService.cancelAuthorization(authorizationToken);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Generate a customer token", description = "Generates a customer token for future purchases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token created successfully",
                    content = @Content(schema = @Schema(implementation = CustomerTokenCreationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Authorization not found"),
            @ApiResponse(responseCode = "409", description = "Data mismatch")
    })
    @PostMapping("/authorizations/{authorizationToken}/customer-token")
    public ResponseEntity<CustomerTokenCreationResponse> generateCustomerToken(
            @Parameter(description = "Authorization token", required = true) @PathVariable String authorizationToken,
            @Valid @RequestBody CustomerTokenCreationRequest tokenRequest) {
        CustomerTokenCreationResponse response = klarnaPaymentService.generateCustomerToken(authorizationToken, tokenRequest);
        return ResponseEntity.ok(response);
    }
}
