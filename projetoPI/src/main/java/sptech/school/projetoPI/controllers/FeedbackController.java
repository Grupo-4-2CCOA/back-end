package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.feedback.FeedbackMapper;
import sptech.school.projetoPI.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.dto.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.services.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Endpoints para gerenciar feedbacks")
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    @Operation(summary = "Cadastrar feedback", description = "Cadastra um novo feedback no sistema.")
    public ResponseEntity<FeedbackResumeResponseDto> signFeedback(@Valid @RequestBody FeedbackRequestDto feedback) {
        Feedback tempFeedback = service.signFeedback(FeedbackMapper.toEntity(feedback));
        return ResponseEntity.status(201).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @GetMapping
    @Operation(summary = "Buscar todos os feedbacks", description = "Busca todos os feedbacks registrados no sistema.")
    public ResponseEntity<List<FeedbackResumeResponseDto>> getAllFeedbacks() {
        List<Feedback> feedbacks = service.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(feedbacks.stream().map(FeedbackMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar feedback por ID", description = "Busca um feedback com base no ID fornecido.")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(FeedbackMapper.toResponseDto(service.getFeedbackById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar feedback por ID", description = "Atualiza as informações de um feedback com base no ID fornecido.")
    public ResponseEntity<FeedbackResumeResponseDto> updateFeedbackById(@Valid @RequestBody FeedbackRequestDto feedback, @PathVariable Integer id) {
        Feedback tempFeedback = service.updateFeedbackById(FeedbackMapper.toEntity(feedback), id);
        return ResponseEntity.status(200).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar feedback por ID", description = "Deleta um feedback com base no ID fornecido.")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {
        return service.deleteFeedbackById(id);
    }
}
