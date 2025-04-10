package sptech.school.projetoPI.dto.feedback;

import sptech.school.projetoPI.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;

import java.time.LocalDateTime;

public class FeedbackResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer rating;
    private String comment;
    private UserResponseDto user;
    private ScheduleResponseDto schedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public ScheduleResponseDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleResponseDto schedule) {
        this.schedule = schedule;
    }
}
