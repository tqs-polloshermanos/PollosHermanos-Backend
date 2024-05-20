package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false, name = "payment_date")
    private LocalDate paymentDate;

    @Column(nullable = false, name = "amount")
    private double amount;

    @Column(nullable = false, name = "card_number")
    private String cardNumber;

    @Column(nullable = false, name = "card_holder_name")
    private String cardHolderName;

    @Column(nullable = false, name = "card_expiry_date")
    private LocalDate cardExpiryDate;

    @Column(nullable = false, name = "card_cvv")
    private String cardCVV;

    // Constructors
    public Payment() {
    }

    public Payment(Order order, LocalDate paymentDate, double amount, String cardNumber, String cardHolderName, LocalDate cardExpiryDate, String cardCVV) {
        this.order = order;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDate getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(LocalDate cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}
