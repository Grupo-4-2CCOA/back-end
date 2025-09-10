package sptech.school.projetoPI.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecases.paymentType.*;
import sptech.school.projetoPI.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.core.application.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.core.application.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.core.application.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mappers.PaymentTypeMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Tipo de pagamento", description = "Endpoints para gerenciar os tipos de pagamento")
public class PaymentTypeController {

    private final CreatePaymentTypeUseCase createPaymentTypeUseCase;
    private final GetAllPaymentTypeUseCase getAllPaymentTypesUseCase;
    private final GetPaymentTypeByIdUseCase getPaymentTypeByIdUseCase;
    private final UpdatePaymentTypeByIdUseCase updatePaymentTypeByIdUseCase;
    private final DeletePaymentTypeByIdUseCase deletePaymentTypeByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar forma de pagamento", description = "Cadastra uma nova forma de pagamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Método de pagamento já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<PaymentTypeResumeResponseDto> createPaymentType(@Valid @RequestBody PaymentTypeRequestDto requestDto) {
        PaymentTypeDomain paymentTypeDomain = PaymentTypeMapper.toDomain(requestDto);
        PaymentTypeDomain createdPaymentTypeDomain = createPaymentTypeUseCase.execute(paymentTypeDomain);
        return new ResponseEntity<>(PaymentTypeMapper.toResumeResponseDto(createdPaymentTypeDomain), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar formas de pagamento", description = "Busca todas as formas de pagamento cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formas de pagamento trazidas com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<PaymentTypeResumeResponseDto>> getAllPaymentTypes() {
        List<PaymentTypeDomain> paymentTypeDomains = getAllPaymentTypesUseCase.execute();
        List<PaymentTypeResumeResponseDto> responseDtos = paymentTypeDomains.stream()
                .map(PaymentTypeMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar forma de pagamento por ID", description = "Busca a forma de pagamento referente ao ID passado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de pagamento encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Integer id) {
        PaymentTypeDomain paymentTypeDomain = getPaymentTypeByIdUseCase.execute(id);
        return ResponseEntity.ok(PaymentTypeMapper.toResponseDto(paymentTypeDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar forma de pagamento", description = "Atualiza as informações da forma de pagamento cujo ID passado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<PaymentTypeResumeResponseDto> updatePaymentTypeById(
            @Valid @RequestBody PaymentTypeRequestDto requestDto,
            @PathVariable Integer id) {
        PaymentTypeDomain paymentTypeDomain = PaymentTypeMapper.toDomain(requestDto);
        PaymentTypeDomain updatedPaymentTypeDomain = updatePaymentTypeByIdUseCase.execute(paymentTypeDomain,id);
        return ResponseEntity.ok(PaymentTypeMapper.toResumeResponseDto(updatedPaymentTypeDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar forma de pagamento por ID", description = "Deleta a forma de pagamento cujo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Forma de pagamento removida com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentTypeResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public void deletePaymentTypeById(@PathVariable Integer id) {
        deletePaymentTypeByIdUseCase.execute(id);
    }
}