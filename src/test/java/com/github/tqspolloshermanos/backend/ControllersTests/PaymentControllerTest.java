package com.github.tqspolloshermanos.backend.ControllersTests;

import com.github.tqspolloshermanos.backend.Controllers.PaymentController;
import com.github.tqspolloshermanos.backend.Entities.Payment;
import com.github.tqspolloshermanos.backend.Services.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testProcessPayment() throws Exception {
        // Mock service response
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        when(paymentService.processPayment(Mockito.any(Payment.class))).thenReturn(payment);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\": 1, \"amount\": 100.0, \"cardNumber\": \"1234-5678-9012-3456\", \"cardHolderName\": \"John Doe\", \"cardExpiryDate\": \"2024-12-31\", \"cardCVV\": \"123\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
