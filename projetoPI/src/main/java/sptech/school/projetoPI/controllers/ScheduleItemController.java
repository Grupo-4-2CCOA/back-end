package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.scheduleItem.ScheduleItemMapper;
import sptech.school.projetoPI.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.entities.ScheduleItem;
import sptech.school.projetoPI.services.ScheduleItemService;

import java.util.List;

@RestController
@RequestMapping("/itens-agendamento")
@RequiredArgsConstructor
public class ScheduleItemController {

    private final ScheduleItemService service;

    @PostMapping
    public ResponseEntity<ScheduleItemResponseDto> signScheduleItem(@Valid @RequestBody ScheduleItemRequestDto scheduleItem) {
        ScheduleItem tempScheduleItem = service.signScheduleItem(ScheduleItemMapper.toEntity(scheduleItem));
        return ResponseEntity.status(201).body(ScheduleItemMapper.toResponseDto(tempScheduleItem));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleItemResponseDto>> getAllScheduleItems() {
        List<ScheduleItem> scheduleItems = service.getAllScheduleItems();

        if (scheduleItems.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(scheduleItems.stream().map(ScheduleItemMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleItemResponseDto> getScheduleItemById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ScheduleItemMapper.toResponseDto(service.getScheduleItemById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleItemResponseDto> updateScheduleItemById(@Valid @RequestBody ScheduleItemRequestDto scheduleItem, @PathVariable Integer id) {
        ScheduleItem tempScheduleItem = service.updateScheduleItemById(ScheduleItemMapper.toEntity(scheduleItem), id);
        return ResponseEntity.status(200).body(ScheduleItemMapper.toResponseDto(tempScheduleItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleItemById(@PathVariable Integer id) {
        service.deleteScheduleItemById(id);
        return ResponseEntity.status(204).build();
    }
}
