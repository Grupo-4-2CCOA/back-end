package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.feedback.FeedbackMapper;
import sptech.school.projetoPI.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.services.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDto> signFeedback(@Valid @RequestBody FeedbackRequestDto feedback) {
        Feedback tempFeedback = service.signFeedback(FeedbackMapper.toEntity(feedback));
        return ResponseEntity.status(201).body(FeedbackMapper.toResponseDto(tempFeedback));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponseDto>> getAllFeedbacks() {
        List<Feedback> feedbacks = service.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(feedbacks.stream().map(FeedbackMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(FeedbackMapper.toResponseDto(service.getFeedbackById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> updateFeedbackById(@Valid @RequestBody FeedbackRequestDto feedback, @PathVariable Integer id) {
        Feedback tempFeedback = service.updateFeedbackById(FeedbackMapper.toEntity(feedback), id);
        return ResponseEntity.status(200).body(FeedbackMapper.toResponseDto(tempFeedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {
        return service.deleteFeedbackById(id);
    }
}
