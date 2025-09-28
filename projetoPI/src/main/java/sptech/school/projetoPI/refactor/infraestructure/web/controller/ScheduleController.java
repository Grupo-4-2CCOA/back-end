package sptech.school.projetoPI.refactor.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.core.application.command.schedule.CreateScheduleCommand;
import sptech.school.projetoPI.refactor.core.application.usecase.schedule.CreateScheduleUseCase;
import sptech.school.projetoPI.refactor.core.application.usecase.schedule.CreateScheduleUsecase;
import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.CreateScheduleResponseDto;
import sptech.school.projetoPI.refactor.infrastructure.mapper.ScheduleMapper;
import sptech.school.projetoPI.refactor.infrastructure.web.dto.schedule.CreateScheduleRequestDto;
import sptech.school.projetoPI.refactor.infrastructure.web.dto.schedule.CreateScheduleResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUsecase;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody @Valid CreateScheduleResponseDto dto
    ) {
        CreateScheduleCommand command = ScheduleMapper.toCreateScheduleCommand(dto);

        ScheduleDomain schedule = createScheduleUsecase.execute(command);

        CreateScheduleResponseDto response = ScheduleMapper.toCreateScheduleResponseDto(schedule);

        return ResponseEntity.status(201).body(response);
    }
}
