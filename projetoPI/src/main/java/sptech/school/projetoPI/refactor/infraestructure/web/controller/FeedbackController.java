package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.core.domain.aggregate.PaymentTypeDomain;
import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.feedback.request.CreateFeedbackRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.feedback.response.FeedbackResponseDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> getFeedback(@PathVariable Integer id) {
        FeedbackResponseDto response = new FeedbackResponseDto(
                id,
                5,
                "Serviço excelente!",
                null
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDto> createFeedback(
            @RequestBody @Valid CreateFeedbackRequestDto request
    ) {
        FeedbackResponseDto response = new FeedbackResponseDto(
                1, // mock de ID
                request.rating(),
                request.comment(),
                null // mock de ScheduleDto
        );

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> updateFeedback(
            @PathVariable Integer id,
            @RequestBody @Valid CreateFeedbackRequestDto request
    ) {
        FeedbackResponseDto response = new FeedbackResponseDto(
                id,
                request.rating(),
                request.comment(),
                null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        return ResponseEntity.noContent().build();
    }
}
