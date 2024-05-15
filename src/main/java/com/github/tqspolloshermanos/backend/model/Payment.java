package com.github.tqspolloshermanos.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false, name = "payment_date")
    private LocalDateTime paymentDate;

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

    public Payment(Order order, LocalDateTime paymentDate, String cardNumber, String cardHolderName, LocalDate cardExpiryDate, String cardCVV) {
        this.order = order;
        this.paymentDate = paymentDate;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
        this.amount = order.getTotalAmount();
    }

    public Payment() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.amount = order.getTotalAmount();
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((cardHolderName == null) ? 0 : cardHolderName.hashCode());
        result = prime * result + ((cardExpiryDate == null) ? 0 : cardExpiryDate.hashCode());
        result = prime * result + ((cardCVV == null) ? 0 : cardCVV.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Double.compare(getAmount(), payment.getAmount()) == 0 &&
                Objects.equals(getPaymentId(), payment.getPaymentId()) &&
                Objects.equals(getOrder(), payment.getOrder()) &&
                Objects.equals(getPaymentDate(), payment.getPaymentDate()) &&
                Objects.equals(getCardNumber(), payment.getCardNumber()) &&
                Objects.equals(getCardHolderName(), payment.getCardHolderName()) &&
                Objects.equals(getCardExpiryDate(), payment.getCardExpiryDate()) &&
                Objects.equals(getCardCVV(), payment.getCardCVV());
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", order=" + order + ", paymentDate=" + paymentDate + ", amount="
                + amount + ", cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName + ", cardExpiryDate="
                + cardExpiryDate + ", cardCVV=" + cardCVV + "]";
    }

}
