package sptech.school.projetoPI.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.services.AbstractService;

import java.time.LocalTime;
import java.util.List;

@Slf4j
public abstract class AbstractController<Entity, Request, Response, ResumeResponse> {

    protected abstract AbstractService<Entity> getService();
    protected abstract Entity toEntity(Request request);
    protected abstract Response toResponse(Entity entity);
    protected abstract ResumeResponse toResumeResponse(Entity entity);

    @PostMapping
    protected ResponseEntity<ResumeResponse> postMethod(Request requestDto) {
        log.info("{} - POST: Executando cadastro de entidade", LocalTime.now());
        return ResponseEntity.status(201).body(
                toResumeResponse(
                        getService().postMethod(toEntity(requestDto))
                ));
    }

    @GetMapping
    protected ResponseEntity<List<ResumeResponse>> getAllMethod() {
        return getService().getAllMethod().isEmpty()?
                ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(getService()
                        .getAllMethod()
                        .stream()
                        .map(this::toResumeResponse)
                        .toList()
                );
    }

    @GetMapping("/{id}")
    protected ResponseEntity<Response> getByIdMethod(Integer id) {
        return ResponseEntity.status(200).body(toResponse(getService().getByIdMethod(id)));
    }

    @PutMapping("/{id}")
    protected ResponseEntity<ResumeResponse> putByIdMethod(Request requestDto, Integer id) {
        return ResponseEntity.status(200).body(
                toResumeResponse(
                        getService().putByIdMethod(toEntity(requestDto), id)
                ));
    }

    @DeleteMapping("/{id}")
    protected ResponseEntity<Void> deleteByIdMethod(Integer id) {
        getService().deleteByIdMethod(id);
        return ResponseEntity.status(204).build();
    }
}
