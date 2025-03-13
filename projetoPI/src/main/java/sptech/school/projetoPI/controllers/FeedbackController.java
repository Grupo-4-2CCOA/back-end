package sptech.school.projetoPI.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Feedback> signFeedback(@RequestBody Feedback feedback) {
        return ResponseEntity.status(201).body(service.signFeedback(feedback));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = service.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getFeedbackById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedbackById(@RequestBody Feedback feedback, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.updateFeedbackById(feedback, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {
        return service.deleteFeedbackById(id);
    }
}
