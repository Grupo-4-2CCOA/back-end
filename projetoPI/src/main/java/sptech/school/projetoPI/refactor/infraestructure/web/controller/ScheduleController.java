package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.request.CreateScheduleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.response.CreateScheduleResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.response.CreateScheduleItemResponseDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final CreateScheduleItemResponseDto MOCKED_ITEM = new CreateScheduleItemResponseDto(
            150.00,
            15.00,
            420
    );

    private final CreateScheduleResponseDto MOCKED_RESPONSE_BASE = new CreateScheduleResponseDto(
            "CONFIRMED",
            LocalDateTime.now().plus(2, ChronoUnit.DAYS).withSecond(0).withNano(0),
            60,
            "HASH_MOCK_012345",
            11,
            22,
            33,
            Set.of(MOCKED_ITEM)
    );

    private CreateScheduleResponseDto toResponseDto(CreateScheduleRequestDto dto) {
        Set<CreateScheduleItemResponseDto> itemResponses = dto.items().stream()
                .map(item -> new CreateScheduleItemResponseDto(
                        item.finalPrice(),
                        item.discount(),
                        item.serviceId()
                ))
                .collect(Collectors.toSet());

        return new CreateScheduleResponseDto(
                dto.status(),
                dto.appointmentDatetime(),
                dto.duration(),
                dto.transactionHash(),
                dto.clientId(),
                dto.employeeId(),
                dto.paymentTypeId(),
                itemResponses
        );
    }

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody @Valid CreateScheduleRequestDto dto
    ) {
        CreateScheduleResponseDto responseDto = toResponseDto(dto);
        System.out.println("Mock: createSchedule - Agendamento criado. Retornando 201 Created.");
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Set<CreateScheduleResponseDto>> getAllScheduleByClient(
            @PathVariable Integer clientId
    ) {
        System.out.printf("Mock: getAllScheduleByClient - Buscando agendamentos do Cliente ID: %d%n", clientId);
        return ResponseEntity.ok(Set.of(MOCKED_RESPONSE_BASE));
    }

    @GetMapping
    public ResponseEntity<Set<CreateScheduleResponseDto>> getAllSchedule()
    {
        Set<CreateScheduleResponseDto> mockList = Set.of(
                MOCKED_RESPONSE_BASE,
                new CreateScheduleResponseDto(
                        "PENDING",
                        LocalDateTime.now().plus(7, ChronoUnit.DAYS).withSecond(0).withNano(0),
                        90,
                        "HASH_MOCK_67890",
                        MOCKED_RESPONSE_BASE.clientId(),
                        99,
                        MOCKED_RESPONSE_BASE.paymentTypeId(),
                        Set.of(MOCKED_ITEM)
                )
        );
        System.out.println("Mock: getAllSchedule - Buscando todos os agendamentos.");
        return ResponseEntity.ok(mockList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateScheduleResponseDto> updateScheduleByIdByClient(
            @PathVariable Integer id,
            @RequestBody @Valid CreateScheduleRequestDto dto
    ) {
        CreateScheduleResponseDto responseDto = toResponseDto(dto);
        System.out.printf("Mock: updateScheduleByIdByClient - Agendamento ID %d atualizado.%n", id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleByIdByClient(
            @PathVariable Integer id
    ) {
        System.out.printf("Mock: deleteScheduleByIdByClient - Agendamento ID %d deletado. Retornando 204 No Content.%n", id);
        return ResponseEntity.noContent().build();
    }

}
