package sptech.school.projetoPI.core.domains;

import java.time.LocalDateTime;


public class Feedback {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private Integer rating;
    private Schedule schedule;
    private Client client;

    public Feedback(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String comment, Integer rating, Schedule schedule, Client client) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.rating = rating;
        this.schedule = schedule;
        this.client = client;
    }

    public Feedback() {
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
