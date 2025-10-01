package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.LocalDateTime;

public class ServiceDomain {
    private Boolean active;
    private String name;
    private Double basePrice;
    private Integer baseDuration;
    private String description;
    private String image;


    public ServiceDomain(Integer id, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Double basePrice, Integer baseDuration, String description, String image) {
        this.active = active;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDuration = baseDuration;
        this.description = description;
        this.image = image;

    }

    public ServiceDomain() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(Integer baseDuration) {
        this.baseDuration = baseDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
