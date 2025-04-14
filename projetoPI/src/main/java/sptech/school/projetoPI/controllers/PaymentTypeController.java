package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.category.CategoryMapper;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeMapper;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.services.PaymentTypeService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PaymentTypeController {

    private final PaymentTypeService service;

    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentTypeResponseDto> signPaymentType(@Valid @RequestBody PaymentTypeRequestDto paymentType) {
        PaymentType tempPaymentType = service.signPaymentType(PaymentTypeMapper.toEntity(paymentType));
        return ResponseEntity.status(201).body(PaymentTypeMapper.toResponseDto(tempPaymentType));
    }

    @GetMapping
    public ResponseEntity<List<PaymentTypeResponseDto>> getAllPaymentTypes() {
        List<PaymentType> payments = service.getAllPaymentTypes();

        if(payments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(payments.stream().map(PaymentTypeMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResponseDto(service.getPaymentTypeById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> updatePaymentTypeById(@RequestBody PaymentTypeRequestDto paymentType, @PathVariable Integer id) {
        PaymentType tempPaymentType = service.updatePaymentTypeById(PaymentTypeMapper.toEntity(paymentType), id);
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResponseDto(tempPaymentType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentTypeById(@PathVariable Integer id) {
        service.deletePaymentTypeById(id);
        return ResponseEntity.status(204).build();
    }
}
