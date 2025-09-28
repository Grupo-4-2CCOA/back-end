package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sptech.school.projetoPI.refactor.core.application.usecase.schedule.CreateScheduleUseCase;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.CreateScheduleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.CreateScheduleResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.CreateScheduleItemResponseDto;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUsecase;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody @Valid CreateScheduleRequestDto dto
    ) {
        CreateScheduleResponseDto createScheduleResponseDto = new CreateScheduleResponseDto(
                dto.status(),
                dto.appointmentDatetime(),
                dto.duration(),
                dto.transactionHash(),
                dto.clientId(),
                dto.employeeId(),
                dto.paymentTypeId(),
                dto.items().stream().map(item -> {
                    CreateScheduleItemResponseDto createScheduleItemResponse = new CreateScheduleItemResponseDto(
                            item.finalPrice(),
                            item.discount(),
                            item.serviceId()
                    );

                    return createScheduleItemResponse;
                }).collect(Collectors.toSet())
        );

        return ResponseEntity.status(201).body(createScheduleResponseDto);
    }
}
