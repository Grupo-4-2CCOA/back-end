package sptech.school.projetoPI.infrastructure.controller;

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
import sptech.school.projetoPI.core.application.usecase.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecase.feedback.*;
import sptech.school.projetoPI.core.domain.FeedbackDomain;
import sptech.school.projetoPI.core.application.command.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.core.application.command.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.core.application.command.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mapper.FeedbackMapper;

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
        FeedbackDomain feedbackDomain = FeedbackMapper.toDomain(requestDto);
        FeedbackDomain createdFeedbackDomain = createFeedbackUseCase.execute(feedbackDomain);
        return new ResponseEntity<>(FeedbackMapper.toResumeResponseDto(createdFeedbackDomain), HttpStatus.CREATED);
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
        List<FeedbackDomain> feedbackDomains = getAllFeedbacksUseCase.execute();
        List<FeedbackResumeResponseDto> responseDtos = feedbackDomains.stream()
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
        FeedbackDomain feedbackDomain = getFeedbackByIdUseCase.execute(id);
        return ResponseEntity.ok(FeedbackMapper.toResponseDto(feedbackDomain));
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
        FeedbackDomain feedbackDomain = FeedbackMapper.toDomain(requestDto);
        FeedbackDomain updatedFeedbackDomain = updateFeedbackByIdUseCase.execute(feedbackDomain, id);
        return ResponseEntity.ok(FeedbackMapper.toResumeResponseDto(updatedFeedbackDomain));
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