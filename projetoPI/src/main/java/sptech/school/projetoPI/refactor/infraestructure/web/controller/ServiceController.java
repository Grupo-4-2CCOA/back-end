package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.category.CreateCategoryResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.request.CreateServiceRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.request.GetServiceByIdRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.request.UpdateServiceByIdRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response.CreateServiceResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response.GetServiceByIdResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response.UpdateServiceByIdResponseDto;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Endpoints para gerenciar serviços")
public class ServiceController {

    @PostMapping
    public ResponseEntity<CreateServiceResponseDto> createService(@Valid @RequestBody CreateServiceRequestDto requestDto) {
        CreateServiceResponseDto createServiceResponseDto = new CreateServiceResponseDto(
                1,
                requestDto.active(),
                requestDto.name(),
                requestDto.basePrice(),
                requestDto.baseDuration(),
                requestDto.description(),
                requestDto.image(),
                new CreateCategoryResponseDto(
                        1,
                        "Olá",
                        "Cabelo"
                )
        );


        return ResponseEntity.status(201).body(createServiceResponseDto);
    }

//    @GetMapping
//    public ResponseEntity<List<ServiceResumeResponseDto>> getAllServices() {
//        List<ServiceDomain> services = getAllServicesUseCase.execute();
//        List<ServiceResumeResponseDto> responseDtos = services.stream()
//                .map(ServiceMapper::toResumeResponseDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseDtos);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GetServiceByIdResponseDto> getServiceById(@PathVariable GetServiceByIdRequestDto id) {

        GetServiceByIdResponseDto createServiceResponseDto = new GetServiceByIdResponseDto(
                1,
                true,
                "jubileu",
                10.99,
                5,
                "Xubiraudaun",
                "show",
                new CreateCategoryResponseDto(
                        1,
                        "Olá",
                        "Cabelo"
                )
        );

        return ResponseEntity.status(200).body(createServiceResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateServiceByIdResponseDto> updateServiceById(@RequestBody UpdateServiceByIdRequestDto name) {
        UpdateServiceByIdResponseDto updateServiceByNameResponseDto = new UpdateServiceByIdResponseDto(
                1,
                true,
                "Jubileu",
                10.99,
                5,
                "show",
                "",
                new CreateCategoryResponseDto(
                        1,
                        "Olá",
                        "Cabelo"
                )

        );
        return ResponseEntity.status(200).body(updateServiceByNameResponseDto);
    }

    @DeleteMapping("/{id}")
    public void deleteServiceById(@PathVariable Integer id) {
        deleteServiceById(id);
    }
}
