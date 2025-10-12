package sptech.school.projetoPI.core.domains;

import org.apache.catalina.User;

import java.time.LocalDateTime;


public class FeedbackDomain {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private Integer rating;
    private ScheduleDomain scheduleDomain;
    private UserDomain userDomain;

    public FeedbackDomain(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String comment, Integer rating, ScheduleDomain scheduleDomain, UserDomain userDomain) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.rating = rating;
        this.scheduleDomain = scheduleDomain;
        this.userDomain = userDomain;
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

    public ScheduleDomain getScheduleDomain() {
        return scheduleDomain;
    }

    public void setScheduleDomain(ScheduleDomain scheduleDomain) {
        this.scheduleDomain = scheduleDomain;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }
}
