package com.github.tqspolloshermanos.backend.ServicesTests;

import com.github.tqspolloshermanos.backend.Entities.Payment;
import com.github.tqspolloshermanos.backend.Repositories.PaymentRepository;
import com.github.tqspolloshermanos.backend.Services.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Test
    void testProcessPayment() {
        // Mock repository response
        PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
        PaymentService paymentService = new PaymentService(paymentRepository);

        Payment payment = new Payment();

        when(paymentRepository.save(payment)).thenReturn(payment);

        // Test service method
        Payment processedPayment = paymentService.processPayment(payment);
        assertEquals(payment, processedPayment);
    }
}
