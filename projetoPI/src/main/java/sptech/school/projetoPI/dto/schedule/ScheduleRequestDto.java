package sptech.school.projetoPI.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class ScheduleRequestDto {
    @NotBlank(message = "Preencha o Status")
    private String status;

    @Positive(message = "ID inválido para Usuário")
    @NotNull(message = "Preencha com o ID do Usuário")
    private Integer user;

    @Positive(message = "ID inválido para Tipo de Pagamento")
    @NotNull(message = "Preencha com o ID do Tipo de Pagamento")
    private Integer paymentType;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "Horário deve estar no futuro")
    private LocalDateTime dateTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
