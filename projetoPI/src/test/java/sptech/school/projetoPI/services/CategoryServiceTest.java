package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.old.core.domains.CategoryDomain;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest extends ServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private ServiceRepository serviceRepository;

    private final CategoryDomain categoryDomain = CategoryDomain.builder()
            .id(1)
            .name("Manicure")
            .description("Manicure")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Category")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(categoryDomain)).thenReturn(categoryDomain);

        CategoryDomain response = service.postMethod(categoryDomain);
        assertEquals(categoryDomain, response);
    }

    @Test
    @DisplayName("Quando já existir Category com o mesmo nome, método postMethod() deve estourar EntityConflictException")
    void executeCategorySignWithExistingCategoryNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(categoryDomain));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Categories na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<CategoryDomain> categories = List.of(
                CategoryDomain.builder()
                        .id(1)
                        .name("Manicure")
                        .build(),

                CategoryDomain.builder()
                        .id(2)
                        .name("Depilação")
                        .build(),

                CategoryDomain.builder()
                        .id(3)
                        .name("Hidratação")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(categories);

        List<CategoryDomain> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Category com ID 1, método getByIdMethod() deve retornar o Category encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(categoryDomain));

        CategoryDomain response = service.getByIdMethod(1);
        assertEquals(categoryDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Category atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new CategoryDomain()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(categoryDomain)).thenReturn(categoryDomain);

        CategoryDomain response = service.putByIdMethod(categoryDomain, anyInt());
        assertEquals(categoryDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(categoryDomain, 1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executeCategoryPutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(categoryDomain, 1));
    }

    @Test
    @DisplayName("Quando nome de Category já estiver registrado, método putByIdMethod() deve estourar EntityConflictException")
    void executeCategoryPutByIdWithExistingCategoryNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(categoryDomain, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(categoryDomain));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(serviceRepository.existsByCategoryId(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(categoryDomain.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeCategoryDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver registrado em um Schedule, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeCategoryDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(serviceRepository.existsByCategoryId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }
}