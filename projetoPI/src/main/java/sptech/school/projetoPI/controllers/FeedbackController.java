package sptech.school.projetoPI.controllers;

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
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.dto.feedback.FeedbackMapper;
import sptech.school.projetoPI.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.dto.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Endpoints para gerenciar feedbacks")
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<FeedbackResumeResponseDto> signFeedback(@Valid @RequestBody FeedbackRequestDto feedback) {
        Feedback tempFeedback = service.signFeedback(FeedbackMapper.toEntity(feedback));
        return ResponseEntity.status(201).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
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
        List<Feedback> feedbacks = service.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(feedbacks.stream().map(FeedbackMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
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
        return ResponseEntity.status(200).body(FeedbackMapper.toResponseDto(service.getFeedbackById(id)));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<FeedbackResumeResponseDto> updateFeedbackById(@Valid @RequestBody FeedbackRequestDto feedback, @PathVariable Integer id) {
        Feedback tempFeedback = service.updateFeedbackById(FeedbackMapper.toEntity(feedback), id);
        return ResponseEntity.status(200).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {
        return service.deleteFeedbackById(id);
    }
}
