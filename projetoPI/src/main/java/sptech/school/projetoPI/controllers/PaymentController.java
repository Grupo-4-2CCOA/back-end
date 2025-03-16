package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Payment;
import sptech.school.projetoPI.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Payment> signPayment(@Valid @RequestBody Payment payment) {
        return ResponseEntity.status(201).body(service.signPayment(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = service.getAllPayments();

        if(payments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getPaymentById(id));
    }
}
