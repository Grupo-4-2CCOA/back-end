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
import sptech.school.projetoPI.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mappers.FeedbackMapper;

import sptech.school.projetoPI.application.usecases.feedback.*; // Importar os UseCases

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Endpoints para gerenciar feedbacks")
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;
    private final GetAllFeedbackUseCase getAllFeedbacksUseCase;
    private final GetFeedbackByIdUseCase getFeedbackByIdUseCase;
    private final UpdateFeedbackByIdUseCase updateFeedbackByIdUseCase;
    private final DeleteFeedbackByIdUseCase deleteFeedbackByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar feedback", description = "Cadastra um novo feedback no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback cadastrado com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
    })
    public ResponseEntity<FeedbackResumeResponseDto> createFeedback(@Valid @RequestBody FeedbackRequestDto requestDto) {
        Feedback feedback = FeedbackMapper.toDomain(requestDto);
        Feedback createdFeedback = createFeedbackUseCase.execute(feedback);
        return new ResponseEntity<>(FeedbackMapper.toResumeResponseDto(createdFeedback), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar todos os feedbacks", description = "Busca todos os feedbacks registrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedbacks trazidos com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<FeedbackResumeResponseDto>> getAllFeedbacks() {
        List<Feedback> feedbacks = getAllFeedbacksUseCase.execute();
        List<FeedbackResumeResponseDto> responseDtos = feedbacks.stream()
                .map(FeedbackMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar feedback por ID", description = "Busca um feedback com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Integer id) {
        Feedback feedback = getFeedbackByIdUseCase.execute(id);
        return ResponseEntity.ok(FeedbackMapper.toResponseDto(feedback));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar feedback por ID", description = "Atualiza as informações de um feedback com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<FeedbackResumeResponseDto> updateFeedbackById(
            @Valid @RequestBody FeedbackRequestDto requestDto,
            @PathVariable Integer id) {
        Feedback feedback = FeedbackMapper.toDomain(requestDto);
        Feedback updatedFeedback = updateFeedbackByIdUseCase.execute(feedback, id);
        return ResponseEntity.ok(FeedbackMapper.toResumeResponseDto(updatedFeedback));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar feedback por ID", description = "Deleta um feedback com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Feedback removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public void deleteFeedbackById(@PathVariable Integer id) {
        deleteFeedbackByIdUseCase.execute(id);
    }
}