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
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Service")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(serviceEntity)).thenReturn(serviceEntity);

        Service response = service.postMethod(serviceEntity);
        assertEquals(serviceEntity, response);
    }

    @Test
    @DisplayName("Quando já existir Service de mesmo nome registrado, método postMethod() deve estourar EntityConflictException")
    void executeServiceSignWithExistingNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(serviceEntity));
    }

    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeServiceSignWithInvalidCategoryIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(serviceEntity));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Services na lista, método getAllMethod() deve retornar tamanho 3")
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

        List<Service> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Service com ID 1, método getByIdMethod() deve retornar o Service encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(serviceEntity));

        Service response = service.getByIdMethod(1);
        assertEquals(serviceEntity, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Service atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Service()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(serviceEntity)).thenReturn(serviceEntity);

        Service response = service.putByIdMethod(serviceEntity, anyInt());
        assertEquals(serviceEntity, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando Service com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executeServicePutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando já existir Service de mesmo nome registrado, método putByIdMethod() deve estourar EntityConflictException")
    void executeServicePutByIdWithExistingNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(serviceEntity, 1));
    }

    @Test
    @DisplayName("Quando não existir Category com ID requisitado em Service, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeServicePutByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(categoryRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(serviceEntity, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(serviceEntity));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(serviceEntity.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Service com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeServiceDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }
}