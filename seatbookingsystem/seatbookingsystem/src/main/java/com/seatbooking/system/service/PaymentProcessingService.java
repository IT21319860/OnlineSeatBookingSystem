package com.seatbooking.system.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seatbooking.system.dto.PaymentGatewayResponse;
import com.seatbooking.system.entity.PaymentEntity;
import com.seatbooking.system.exception.InvalidPaymentRequestException;
import com.seatbooking.system.exception.PaymentNotFoundException;
import com.seatbooking.system.exception.PaymentProcessingException;
import com.seatbooking.system.repository.PaymentRepository;

@Service
public class PaymentProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayIntegration paymentGatewayIntegration;

    @Autowired
    public PaymentProcessingService(PaymentRepository paymentRepository, PaymentGatewayIntegration paymentGatewayIntegration) {
        this.paymentRepository = paymentRepository;
        this.paymentGatewayIntegration = paymentGatewayIntegration;
    }

    @Transactional
    public PaymentEntity processPayment(PaymentEntity paymentRequest) throws PaymentProcessingException {
        validatePaymentRequest(paymentRequest);

        logger.info("Processing payment: {}", paymentRequest);

        // Call external payment gateway (e.g., Stripe, PayPal)
        PaymentGatewayResponse gatewayResponse = paymentGatewayIntegration.processPayment(paymentRequest);

        if (!gatewayResponse.isSuccess()) {
            throw new PaymentProcessingException("Payment failed: " + gatewayResponse.getErrorMessage());
        }

        paymentRequest.setPaymentReferenceId(generatePaymentReferenceId());
        paymentRequest.setTransactionId(gatewayResponse.getTransactionId());
        paymentRequest.setStatus("COMPLETED");
        paymentRequest.setTransactionDate(LocalDateTime.now());

        PaymentEntity savedPayment = paymentRepository.save(paymentRequest);
        logger.info("Payment processed successfully: {}", savedPayment);

        return savedPayment;
    }

    @Transactional(readOnly = true)
    public PaymentEntity retrievePaymentStatus(String paymentReferenceId) throws PaymentNotFoundException {
        PaymentEntity payment = paymentRepository.findByPaymentReferenceId(paymentReferenceId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with Reference ID: " + paymentReferenceId));
        logger.info("Retrieved Payment Status: {}", payment);
        return payment;
    }

    @Transactional
    public PaymentEntity updatePayment(String paymentReferenceId, PaymentEntity updatedPayment) 
            throws PaymentNotFoundException, InvalidPaymentRequestException {
        validatePaymentRequest(updatedPayment);

        PaymentEntity existingPayment = paymentRepository.findByPaymentReferenceId(paymentReferenceId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with Reference ID: " + paymentReferenceId));

        existingPayment.setAmount(updatedPayment.getAmount());
        existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
        existingPayment.setStatus(updatedPayment.getStatus());
        existingPayment.setTransactionDate(LocalDateTime.now());

        PaymentEntity updatedPaymentEntity = paymentRepository.save(existingPayment);
        logger.info("Payment updated successfully: {}", updatedPaymentEntity);

        return updatedPaymentEntity;
    }

    @Transactional
    public void deletePayment(String paymentReferenceId) throws PaymentNotFoundException {
        PaymentEntity existingPayment = paymentRepository.findByPaymentReferenceId(paymentReferenceId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with Reference ID: " + paymentReferenceId));
        
        paymentRepository.delete(existingPayment);
        logger.info("Payment deleted successfully with Reference ID: {}", paymentReferenceId);
    }

    private void validatePaymentRequest(PaymentEntity payment) throws InvalidPaymentRequestException {
        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            throw new InvalidPaymentRequestException("Payment amount must be greater than zero.");
        }
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new InvalidPaymentRequestException("Payment method is required.");
        }
    }

    private String generatePaymentReferenceId() {
        return "REF-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
