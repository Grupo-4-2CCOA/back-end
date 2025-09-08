package sptech.school.projetoPI.core.domain;

import java.time.LocalDateTime;


public class FeedbackDomain {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private Integer rating;
    private ScheduleDomain scheduleDomain;
    private ClientDomain clientDomain;

    public FeedbackDomain(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String comment, Integer rating, ScheduleDomain scheduleDomain, ClientDomain clientDomain) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.rating = rating;
        this.scheduleDomain = scheduleDomain;
        this.clientDomain = clientDomain;
    }

    public FeedbackDomain() {
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

    public ScheduleDomain getSchedule() {
        return scheduleDomain;
    }

    public void setSchedule(ScheduleDomain scheduleDomain) {
        this.scheduleDomain = scheduleDomain;
    }

    public ClientDomain getClient() {
        return clientDomain;
    }

    public void setClient(ClientDomain clientDomain) {
        this.clientDomain = clientDomain;
    }
}
