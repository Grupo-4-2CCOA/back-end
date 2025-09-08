package sptech.school.projetoPI.core.domains;

import java.time.LocalDateTime;

public class ScheduleItem {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double finalPrice;
    private Double discount;
    private Schedule schedule;
    private ServiceDomain service;

    public ScheduleItem(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Double finalPrice, Double discount, Schedule schedule, ServiceDomain service) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.finalPrice = finalPrice;
        this.discount = discount;
        this.schedule = schedule;
        this.service = service;
    }

    public ScheduleItem() {

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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ServiceDomain getService() {
        return service;
    }

    public void setService(ServiceDomain service) {
        this.service = service;
    }
}
