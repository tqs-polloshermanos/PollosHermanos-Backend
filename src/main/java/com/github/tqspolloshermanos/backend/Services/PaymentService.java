package com.github.tqspolloshermanos.backend.Services;

import com.github.tqspolloshermanos.backend.Entities.EStatus;
import com.github.tqspolloshermanos.backend.Entities.Payment;
import com.github.tqspolloshermanos.backend.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment processPayment(Payment payment) {
        // Implement logic for processing payment, e.g., saving to database
        return paymentRepository.save(payment);
    }

    public Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }
}
