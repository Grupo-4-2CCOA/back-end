package sptech.school.projetoPI.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.projetoPI.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecases.service.*;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.application.dto.service.ServiceRequestDto;
import sptech.school.projetoPI.core.application.dto.service.ServiceResponseDto;
import sptech.school.projetoPI.core.application.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mappers.ServiceMapper;
import sptech.school.projetoPI.infrastructure.mappers.ServiceMapperImage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Endpoints para gerenciar serviços")
public class ServiceController {

    private final CreateServiceUseCase createServiceUseCase;
    private final GetAllServiceUseCase getAllServicesUseCase;
    private final GetServiceByIdUseCase getServiceByIdUseCase;
    private final UpdateServiceByIdUseCase updateServiceByIdUseCase;
    private final DeleteServiceByIdUseCase deleteServiceByIdUseCase;
    private final ServiceMapperImage serviceMapperImage;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar novo serviço", description = "Cadastra um novo serviço a partir do json fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Serviço já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<ServiceResponseDto> createService(
            @RequestPart("service") @Valid ServiceRequestDto requestDto,
            @RequestPart("image") MultipartFile image
    ) throws IOException {

        System.out.println("Categoria recebida: " + requestDto.getCategory());
        if (requestDto.getCategory() != null) {
            System.out.println("ID da categoria recebida: " + requestDto.getCategory().getId());
        }

        ServiceDomain service = ServiceMapper.toDomain(requestDto);

        ServiceDomain createdService = createServiceUseCase.execute(
                service,
                image.getBytes(),
                image.getOriginalFilename()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceMapperImage.toResponseDtoWithImageUrl(createdService));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar todos os serviços", description = "Busca todos os serviços cadastros no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços trazidos com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<ServiceResumeResponseDto>> getAllServices() {
        List<ServiceDomain> services = getAllServicesUseCase.execute();
        List<ServiceResumeResponseDto> responseDtos = services.stream()
                .map(ServiceMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar serviço por ID", description = "Busca o serviço pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable Integer id) {
        ServiceDomain service = getServiceByIdUseCase.execute(id);
        return ResponseEntity.ok(ServiceMapper.toResponseDto(service));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar serviço por ID", description = "Atualiza as informações do serviço com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ServiceResumeResponseDto> updateServiceById(@Valid @RequestBody ServiceRequestDto requestDto, @PathVariable Integer id) {
        ServiceDomain service = ServiceMapper.toDomain(requestDto);
        ServiceDomain updatedService = updateServiceByIdUseCase.execute(service, id);
        return ResponseEntity.ok(ServiceMapper.toResumeResponseDto(updatedService));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar serviço por ID", description = "Deleta o serviço cujo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Serviço removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public void deleteServiceById(@PathVariable Integer id) {
        deleteServiceByIdUseCase.execute(id);
    }
}