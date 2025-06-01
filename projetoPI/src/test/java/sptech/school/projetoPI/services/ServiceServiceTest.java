package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.entities.Service;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.CategoryRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ServiceServiceTest extends ServiceTest {

    @InjectMocks
    private ServiceService service;

    @Mock
    private ServiceRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private final Category category = Category.builder()
            .id(1)
            .name("Default Category")
            .active(true)
            .build();

    private final Service serviceEntity = Service.builder()
            .id(1)
            .category(category)
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .baseDuration(10)
            .basePrice(10.0)
            .name("Service")
            .description("Default Service")
            .image("Image URL")
            .build();


    @Override
    @Test
    @DisplayName("Quando método SignService() for chamado com credenciais válidas, deve retornar Service")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(serviceEntity)).thenReturn(serviceEntity);

        Service response = service.signService(serviceEntity);
        assertEquals(serviceEntity, response);
    }

    @Test
    @DisplayName("Quando já existir Service de mesmo nome registrado, método SignService() deve estourar EntityConflictException")
    void executeServiceSignWithExistingNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signService(serviceEntity));
    }

    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método SignService() deve estourar RelatedEntityNotFoundException")
    void executeServiceSignWithInvalidCategoryIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signService(serviceEntity));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Services na lista, método GetAllServices() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Service> services = List.of(
                Service.builder()
                        .id(1)
                        .active(true)
                        .description("Service 1")
                        .build(),

                Service.builder()
                        .id(2)
                        .active(true)
                        .description("Service 2")
                        .build(),

                Service.builder()
                        .id(3)
                        .active(true)
                        .description("Service 3")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(services);

        List<Service> response = service.getAllServices();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Service com ID 1, método GetServiceById() deve retornar o Service encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(serviceEntity));

        Service response = service.getServiceById(1);
        assertEquals(serviceEntity, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método GetServiceById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getServiceById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdateServiceById() for chamado com credenciais válidas, deve retornar Service atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Service()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(serviceEntity)).thenReturn(serviceEntity);

        Service response = service.updateServiceById(serviceEntity, anyInt());
        assertEquals(serviceEntity, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método UpdateServiceById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateServiceById(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando Service com ID requisitado estiver inativo, método UpdateServiceById() deve estourar InactiveEntityException")
    void executeServiceUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.updateServiceById(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando já existir Service de mesmo nome registrado, método UpdateServiceById() deve estourar EntityConflictException")
    void executeServiceUpdateByIdWithExistingNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updateServiceById(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando não existir Category com ID requisitado em Service, método UpdateServiceById() deve estourar RelatedEntityNotFoundException")
    void executeServiceUpdateByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateServiceById(serviceEntity, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método DeleteServiceById() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(serviceEntity));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);

        service.deleteServiceById(1);

        assertFalse(serviceEntity.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método DeleteServiceById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteServiceById(1));
    }

    @Test
    @DisplayName("Quando Service com ID requisitado estiver inativo, método DeleteServiceById() deve estourar InactiveEntityException")
    void executeServiceDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteServiceById(1));
    }
}