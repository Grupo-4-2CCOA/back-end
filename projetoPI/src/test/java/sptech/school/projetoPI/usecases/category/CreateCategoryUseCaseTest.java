package sptech.school.projetoPI.usecases.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.category.CreateCategoryUseCase;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    @DisplayName("Deve criar categoria com sucesso quando dados são válidos")
    void deveCriarCategoriaComSucesso() {
        // Arrange
        CategoryDomain category = new CategoryDomain();
        category.setName("Categoria Teste");
        category.setDescription("Descrição da categoria");
        category.setActive(true);

        CategoryDomain savedCategory = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Teste", "Descrição da categoria");

        when(categoryGateway.existsByName(anyString())).thenReturn(false);
        when(categoryGateway.save(any(CategoryDomain.class))).thenReturn(savedCategory);

        // Act
        CategoryDomain result = createCategoryUseCase.execute(category);

        // Assert
        assertNotNull(result);
        assertEquals(savedCategory.getId(), result.getId());
        assertEquals(savedCategory.getName(), result.getName());
        verify(categoryGateway, times(1)).existsByName(anyString());
        verify(categoryGateway, times(1)).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando categoria com mesmo nome já existe")
    void deveLancarExcecaoQuandoCategoriaJaExiste() {
        // Arrange
        CategoryDomain category = new CategoryDomain();
        category.setName("Categoria Existente");

        when(categoryGateway.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            createCategoryUseCase.execute(category);
        });

        verify(categoryGateway, times(1)).existsByName(anyString());
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }
}

