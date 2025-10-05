package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.request.CreateScheduleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.response.CreateScheduleResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.response.CreateScheduleItemResponseDto;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

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
