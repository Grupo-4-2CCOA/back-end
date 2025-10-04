package sptech.school.projetoPI.refactor.core.domain.aggregate;

public class FeedbackDomain {
  private Integer id;
  private Integer rating;
  private String comment;
  private ScheduleDomain scheduleDomain;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
