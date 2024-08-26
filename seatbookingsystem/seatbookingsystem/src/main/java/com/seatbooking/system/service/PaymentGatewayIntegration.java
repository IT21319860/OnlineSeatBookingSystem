package com.seatbooking.system.service;

import com.seatbooking.system.dto.PaymentGatewayResponse;
import com.seatbooking.system.entity.PaymentEntity;

public interface PaymentGatewayIntegration {
    PaymentGatewayResponse processPayment(PaymentEntity paymentRequest);
    
}

