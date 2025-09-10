package sptech.school.projetoPI.core.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AvailabilityDomain {
    private Integer id;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private EmployeeDomain employeeDomain;

    public AvailabilityDomain(Integer id, Boolean isAvailable, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate day, LocalTime startTime, LocalTime endTime, EmployeeDomain employeeDomain) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeDomain = employeeDomain;
    }

    public AvailabilityDomain() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public EmployeeDomain getEmployee() {
        return employeeDomain;
    }

    public void setEmployee(EmployeeDomain employeeDomain) {
        this.employeeDomain = employeeDomain;
    }
}
