package sptech.school.projetoPI.usecases.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.category.GetCategoryByIdUseCase;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCategoryByIdUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Test
    @DisplayName("Deve retornar categoria quando ID existe e está ativo")
    void deveRetornarCategoriaQuandoIdExiste() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Teste", "Descrição");

        when(categoryGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.of(category));

        // Act
        CategoryDomain result = getCategoryByIdUseCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Categoria Teste", result.getName());
        verify(categoryGateway, times(1)).findByIdAndActiveTrue(id);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando categoria não existe")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        // Arrange
        Integer id = 999;
        when(categoryGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            getCategoryByIdUseCase.execute(id);
        });

        verify(categoryGateway, times(1)).findByIdAndActiveTrue(id);
    }
}

