package com.seatbooking.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments") // Ensure table name matches your database schema
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     
    private Long id;

    @Column(name = "payment_reference_id", nullable = false, unique = true)
    @JsonProperty("payment_reference_id") // Map the JSON property "payment_reference_id" to this field
    private String paymentReferenceId;

    @Column(name = "transaction_id")
    @JsonProperty("transaction_id") // Map the JSON property "transaction_id" to this field
    private String transactionId;

    @Column(name = "status")
    @JsonProperty("status") // Map the JSON property "status" to this field
    private String status;

    @Column(name = "amount")
    @JsonProperty("amount") // Map the JSON property "amount" to this field
    private Double amount;

    @Column(name = "payment_method")
    @JsonProperty("payment_method") // Map the JSON property "payment_method" to this field
    private String paymentMethod;

    @Column(name = "currency")
    @JsonProperty("currency") // Map the JSON property "currency" to this field
    private String currency;

    @Column(name = "transaction_date")
    @JsonProperty("transaction_date") // Map the JSON property "transaction_date" to this field
    private LocalDateTime transactionDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}

