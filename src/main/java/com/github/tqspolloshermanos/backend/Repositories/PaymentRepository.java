package com.github.tqspolloshermanos.backend.Repositories;

import com.github.tqspolloshermanos.backend.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
}
