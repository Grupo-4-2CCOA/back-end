package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeMapper;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.services.PaymentTypeService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService service;

    @PostMapping
    public ResponseEntity<PaymentTypeResumeResponseDto> signPaymentType(@Valid @RequestBody PaymentTypeRequestDto paymentType) {
        PaymentType tempPaymentType = service.signPaymentType(PaymentTypeMapper.toEntity(paymentType));
        return ResponseEntity.status(201).body(PaymentTypeMapper.toResumeResponseDto(tempPaymentType));
    }

    @GetMapping
    public ResponseEntity<List<PaymentTypeResumeResponseDto>> getAllPaymentTypes() {
        List<PaymentType> payments = service.getAllPaymentTypes();

        if(payments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(payments.stream().map(PaymentTypeMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResponseDto(service.getPaymentTypeById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeResumeResponseDto> updatePaymentTypeById(@RequestBody PaymentTypeRequestDto paymentType, @PathVariable Integer id) {
        PaymentType tempPaymentType = service.updatePaymentTypeById(PaymentTypeMapper.toEntity(paymentType), id);
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResumeResponseDto(tempPaymentType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentTypeById(@PathVariable Integer id) {
        service.deletePaymentTypeById(id);
        return ResponseEntity.status(204).build();
    }
}
