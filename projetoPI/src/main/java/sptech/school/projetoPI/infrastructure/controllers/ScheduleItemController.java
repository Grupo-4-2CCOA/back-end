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
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemMapper;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.infrastructure.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.ScheduleItemService;

import java.util.List;

@RestController
@RequestMapping("/itens-agendamento")
@RequiredArgsConstructor
@Tag(name = "Item do pedido", description = "Endpoints para gerenciar os itens do pedido")
public class ScheduleItemController extends AbstractController<ScheduleItem, ScheduleItemRequestDto, ScheduleItemResponseDto, ScheduleItemResumeResponseDto> {

    private final ScheduleItemService service;

    @Override
    public ScheduleItemService getService() {
        return service;
    }

    @Override
    public ScheduleItem toEntity(ScheduleItemRequestDto requestDto) {
        return ScheduleItemMapper.toEntity(requestDto);
    }

    @Override
    public ScheduleItemResponseDto toResponse(ScheduleItem entity) {
        return ScheduleItemMapper.toResponseDto(entity);
    }

    @Override
    public ScheduleItemResumeResponseDto toResumeResponse(ScheduleItem entity) {
        return ScheduleItemMapper.toResumeResponseDto(entity);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar agendamento", description = "Cadastra um novo agendamento no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
    })
    public ResponseEntity<ScheduleItemResumeResponseDto> postMethod(@Valid @RequestBody ScheduleItemRequestDto requestDto) {
        return super.postMethod(requestDto);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar agendamentos", description = "Busca todos os agendamentos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos trazidos com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<ScheduleItemResumeResponseDto>> getAllMethod() {
        return super.getAllMethod();
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ScheduleItemResponseDto> getByIdMethod(@PathVariable Integer id) {
        return super.getByIdMethod(id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar agendamento por ID", description = "Atualiza um agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ScheduleItemResumeResponseDto> putByIdMethod(@Valid @RequestBody ScheduleItemRequestDto requestDto, @PathVariable Integer id) {
        return super.putByIdMethod(requestDto, id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar agendamento por ID ", description = "Deleta um agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleItemResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<Void> deleteByIdMethod(@PathVariable Integer id) {
        return super.deleteByIdMethod(id);
    }
}
