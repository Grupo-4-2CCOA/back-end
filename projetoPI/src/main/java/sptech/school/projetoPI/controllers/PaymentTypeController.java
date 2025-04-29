package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tipo de pagamento", description = "Endpoints para gerenciar os tipos de pagamento")
public class PaymentTypeController {

    private final PaymentTypeService service;

    @PostMapping
    @Operation(summary = "Cadastrar forma de pagamento", description = "Cadastra uma nova forma de pagamento.")
    public ResponseEntity<PaymentTypeResumeResponseDto> signPaymentType(@Valid @RequestBody PaymentTypeRequestDto paymentType) {
        PaymentType tempPaymentType = service.signPaymentType(PaymentTypeMapper.toEntity(paymentType));
        return ResponseEntity.status(201).body(PaymentTypeMapper.toResumeResponseDto(tempPaymentType));
    }

    @GetMapping
    @Operation(summary = "Buscar formas de pagamento", description = "Busca todas as formas de pagamento cadastradas no sistema.")
    public ResponseEntity<List<PaymentTypeResumeResponseDto>> getAllPaymentTypes() {
        List<PaymentType> payments = service.getAllPaymentTypes();

        if(payments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(payments.stream().map(PaymentTypeMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar forma de pagamento por ID", description = "Busca a forma de pagamento referente ao ID passado.")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResponseDto(service.getPaymentTypeById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar forma de pagamento", description = "Atualiza as informações da forma de pagamento cujo ID passado.")
    public ResponseEntity<PaymentTypeResumeResponseDto> updatePaymentTypeById(@RequestBody PaymentTypeRequestDto paymentType, @PathVariable Integer id) {
        PaymentType tempPaymentType = service.updatePaymentTypeById(PaymentTypeMapper.toEntity(paymentType), id);
        return ResponseEntity.status(200).body(PaymentTypeMapper.toResumeResponseDto(tempPaymentType));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar forma de pagamento por ID", description = "Deleta a forma de pagamento cujo ID fornecido.")
    public ResponseEntity<Void> deletePaymentTypeById(@PathVariable Integer id) {
        service.deletePaymentTypeById(id);
        return ResponseEntity.status(204).build();
    }
}
