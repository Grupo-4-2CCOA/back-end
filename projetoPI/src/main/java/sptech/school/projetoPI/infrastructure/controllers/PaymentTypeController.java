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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.controllers.AbstractController;
import sptech.school.projetoPI.infrastructure.mappers.PaymentTypeMapper;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.infrastructure.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.PaymentTypeService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Tipo de pagamento", description = "Endpoints para gerenciar os tipos de pagamento")
public class PaymentTypeController extends AbstractController<PaymentType, PaymentTypeRequestDto, PaymentTypeResponseDto, PaymentTypeResumeResponseDto> {

    private final PaymentTypeService service;

    @Override
    public PaymentTypeService getService() {
        return service;
    }

    @Override
    public PaymentType toEntity(PaymentTypeRequestDto requestDto) {
        return PaymentTypeMapper.toEntity(requestDto);
    }

    @Override
    public PaymentTypeResponseDto toResponse(PaymentType entity) {
        return PaymentTypeMapper.toResponseDto(entity);
    }

    @Override
    public PaymentTypeResumeResponseDto toResumeResponse(PaymentType entity) {
        return PaymentTypeMapper.toResumeResponseDto(entity);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<PaymentTypeResumeResponseDto> postMethod(@Valid @RequestBody PaymentTypeRequestDto requestDto) {
        return super.postMethod(requestDto);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<List<PaymentTypeResumeResponseDto>> getAllMethod() {
        return super.getAllMethod();
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<PaymentTypeResponseDto> getByIdMethod(@PathVariable Integer id) {
        return super.getByIdMethod(id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<PaymentTypeResumeResponseDto> putByIdMethod(@Valid @RequestBody PaymentTypeRequestDto requestDto, @PathVariable Integer id) {
        return super.putByIdMethod(requestDto, id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<Void> deleteByIdMethod(@PathVariable Integer id) {
        return super.deleteByIdMethod(id);
    }
}
