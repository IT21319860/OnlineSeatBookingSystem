package com.seatbooking.system.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.seatbooking.system.dto.PaymentGatewayResponse;
import com.seatbooking.system.entity.PaymentEntity;

@Service
public class StripePaymentGatewayIntegration implements PaymentGatewayIntegration {

    @Override
    public PaymentGatewayResponse processPayment(PaymentEntity paymentRequest) {
        // Simulated external API call to Stripe
        PaymentGatewayResponse response = new PaymentGatewayResponse();
        response.setSuccess(true);
        response.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8));
        return response;
    }

	
}