package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.Instant;

public class FeedbackDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private Integer rating;
  private String comment;
  private ScheduleDomain scheduleDomain;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
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

  public ScheduleDomain getScheduleDomain() {
    return scheduleDomain;
  }

  public void setScheduleDomain(ScheduleDomain scheduleDomain) {
    this.scheduleDomain = scheduleDomain;
  }
}
