package sptech.school.projetoPI.core.domains;

import java.time.LocalDateTime;

public class ScheduleItemDomain {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double finalPrice;
    private Double discount;
    private ScheduleDomain scheduleDomain;
    private ServiceDomain service;

    public ScheduleItemDomain(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Double finalPrice, Double discount, ScheduleDomain scheduleDomain, ServiceDomain service) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.finalPrice = finalPrice;
        this.discount = discount;
        this.scheduleDomain = scheduleDomain;
        this.service = service;
    }

    public ScheduleItemDomain() {

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

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public ScheduleDomain getSchedule() {
        return scheduleDomain;
    }

    public void setSchedule(ScheduleDomain scheduleDomain) {
        this.scheduleDomain = scheduleDomain;
    }

    public ServiceDomain getService() {
        return service;
    }

    public void setService(ServiceDomain service) {
        this.service = service;
    }
}
