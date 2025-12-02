package sptech.school.projetoPI.usecases.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.category.UpdateCategoryByIdUseCase;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryByIdUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private UpdateCategoryByIdUseCase updateCategoryByIdUseCase;

    @Test
    @DisplayName("Deve atualizar categoria com sucesso quando dados são válidos")
    void deveAtualizarCategoriaComSucesso() {
        // Arrange
        Integer id = 1;
        CategoryDomain existingCategory = new CategoryDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Antiga", "Descrição Antiga");

        CategoryDomain updatedCategory = new CategoryDomain();
        updatedCategory.setName("Categoria Atualizada");
        updatedCategory.setDescription("Nova Descrição");

        CategoryDomain savedCategory = new CategoryDomain(id, true, existingCategory.getCreatedAt(), LocalDateTime.now(),
                "Categoria Atualizada", "Nova Descrição");

        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(categoryGateway.existsByIdNotAndName(id, updatedCategory.getName())).thenReturn(false);
        when(categoryGateway.findById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryGateway.save(any(CategoryDomain.class))).thenReturn(savedCategory);

        // Act
        CategoryDomain result = updateCategoryByIdUseCase.execute(updatedCategory, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Categoria Atualizada", result.getName());
        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(categoryGateway, times(1)).existsByIdNotAndName(id, updatedCategory.getName());
        verify(categoryGateway, times(1)).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando categoria não existe")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        // Arrange
        Integer id = 999;
        CategoryDomain category = new CategoryDomain();
        category.setName("Categoria Teste");

        when(categoryGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            updateCategoryByIdUseCase.execute(category, id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando categoria está inativa")
    void deveLancarExcecaoQuandoCategoriaEstaInativa() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain();
        category.setName("Categoria Teste");

        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            updateCategoryByIdUseCase.execute(category, id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando nome já existe em outra categoria")
    void deveLancarExcecaoQuandoNomeJaExiste() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain();
        category.setName("Categoria Existente");

        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(categoryGateway.existsByIdNotAndName(id, category.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            updateCategoryByIdUseCase.execute(category, id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(categoryGateway, times(1)).existsByIdNotAndName(id, category.getName());
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }
}

