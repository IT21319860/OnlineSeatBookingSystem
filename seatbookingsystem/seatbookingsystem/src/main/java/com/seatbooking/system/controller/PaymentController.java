package com.seatbooking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seatbooking.system.entity.PaymentEntity;
import com.seatbooking.system.exception.InvalidPaymentRequestException;
import com.seatbooking.system.exception.PaymentNotFoundException;
import com.seatbooking.system.exception.PaymentProcessingException;
import com.seatbooking.system.service.PaymentProcessingService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentProcessingService paymentProcessingService;

    // Endpoint to initiate a payment
    @PostMapping("/process")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentEntity paymentRequest) {
        try {
            PaymentEntity processedPayment = paymentProcessingService.processPayment(paymentRequest);
            return ResponseEntity.ok(processedPayment);
        } catch (InvalidPaymentRequestException | PaymentProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to check payment status
    @GetMapping("/status/{paymentReferenceId}")
    public ResponseEntity<?> checkPaymentStatus(@PathVariable String paymentReferenceId) {
        try {
            PaymentEntity paymentStatus = paymentProcessingService.retrievePaymentStatus(paymentReferenceId);
            return ResponseEntity.ok(paymentStatus);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint to update payment details
    @PutMapping("/update/{paymentReferenceId}")
    public ResponseEntity<?> updatePayment(@PathVariable String paymentReferenceId, @RequestBody PaymentEntity paymentRequest) {
        try {
            PaymentEntity updatedPayment = paymentProcessingService.updatePayment(paymentReferenceId, paymentRequest);
            return ResponseEntity.ok(updatedPayment);
        } catch (InvalidPaymentRequestException | PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to delete a payment
    @DeleteMapping("/delete/{paymentReferenceId}")
    public ResponseEntity<?> deletePayment(@PathVariable String paymentReferenceId) {
        try {
            paymentProcessingService.deletePayment(paymentReferenceId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
