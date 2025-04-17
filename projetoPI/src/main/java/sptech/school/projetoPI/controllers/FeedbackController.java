package sptech.school.projetoPI.controllers;

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
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<FeedbackResumeResponseDto> signFeedback(@Valid @RequestBody FeedbackRequestDto feedback) {
        Feedback tempFeedback = service.signFeedback(FeedbackMapper.toEntity(feedback));
        return ResponseEntity.status(201).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResumeResponseDto>> getAllFeedbacks() {
        List<Feedback> feedbacks = service.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(feedbacks.stream().map(FeedbackMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(FeedbackMapper.toResponseDto(service.getFeedbackById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResumeResponseDto> updateFeedbackById(@Valid @RequestBody FeedbackRequestDto feedback, @PathVariable Integer id) {
        Feedback tempFeedback = service.updateFeedbackById(FeedbackMapper.toEntity(feedback), id);
        return ResponseEntity.status(200).body(FeedbackMapper.toResumeResponseDto(tempFeedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {
        return service.deleteFeedbackById(id);
    }
}
