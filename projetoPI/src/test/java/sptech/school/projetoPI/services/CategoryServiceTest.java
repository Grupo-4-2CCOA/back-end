package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.CategoryRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

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

    private final Category category = Category.builder()
            .id(1)
            .name("Manicure")
            .description("Manicure")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método SignCategory() for chamado com credenciais válidas, deve retornar Category")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(category)).thenReturn(category);

        Category response = service.signCategory(category);
        assertEquals(category, response);
    }

    @Test
    @DisplayName("Quando já existir Category com o mesmo nome, método SignCategory() deve estourar EntityConflictException")
    void executeCategorySignWithExistingCategoryNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signCategory(category));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Categories na lista, método GetAllCategories() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Category> categories = List.of(
                Category.builder()
                        .id(1)
                        .name("Manicure")
                        .build(),

                Category.builder()
                        .id(2)
                        .name("Depilação")
                        .build(),

                Category.builder()
                        .id(3)
                        .name("Hidratação")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(categories);

        List<Category> response = service.getAllCategories();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Category com ID 1, método GetCategoryById() deve retornar o Category encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(category));

        Category response = service.getCategoryById(1);
        assertEquals(category, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método GetCategoryById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getCategoryById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdateCategoryById() for chamado com credenciais válidas, deve retornar Category atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Category()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(category)).thenReturn(category);

        Category response = service.updateCategoryById(category, anyInt());
        assertEquals(category, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método UpdateCategoryById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateCategoryById(category, 1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver inativo, método UpdateCategoryById() deve estourar InactiveEntityException")
    void executeCategoryUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.updateCategoryById(category, 1));
    }

    @Test
    @DisplayName("Quando nome de Category já estiver registrado, método UpdateCategoryById() deve estourar EntityConflictException")
    void executeCategoryUpdateByIdWithExistingCategoryNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updateCategoryById(category, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método DeleteCategoryById() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(category));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(serviceRepository.existsByCategoryId(anyInt())).thenReturn(false);

        service.deleteCategoryById(1);

        assertFalse(category.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Category com ID requisitado, método DeleteCategoryById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteCategoryById(1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver inativo, método DeleteCategoryById() deve estourar InactiveEntityException")
    void executeCategoryDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteCategoryById(1));
    }

    @Test
    @DisplayName("Quando Category com ID requisitado estiver registrado em um Schedule, método DeleteCategoryById() deve estourar ForeignKeyConstraintException")
    void executeCategoryDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(serviceRepository.existsByCategoryId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteCategoryById(1));
    }
}