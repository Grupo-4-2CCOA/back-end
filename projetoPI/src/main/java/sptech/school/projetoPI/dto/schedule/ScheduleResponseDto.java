package sptech.school.projetoPI.dto.schedule;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

public class ScheduleResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime dateTime;

    private UserResponseDto user;
    private PaymentTypeResponseDto paymentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public PaymentTypeResponseDto getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeResponseDto paymentType) {
        this.paymentType = paymentType;
    }
}
