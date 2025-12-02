package sptech.school.projetoPI.usecases.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.category.GetAllCategoryUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private GetAllCategoryUseCase getAllCategoryUseCase;

    @Test
    @DisplayName("Deve retornar lista de categorias quando existem categorias ativas")
    void deveRetornarListaDeCategorias() {
        // Arrange
        CategoryDomain category1 = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria 1", "Descrição 1");
        CategoryDomain category2 = new CategoryDomain(2, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria 2", "Descrição 2");

        List<CategoryDomain> categories = Arrays.asList(category1, category2);

        when(categoryGateway.findAllByActiveTrue()).thenReturn(categories);

        // Act
        List<CategoryDomain> result = getAllCategoryUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Categoria 1", result.get(0).getName());
        assertEquals("Categoria 2", result.get(1).getName());
        verify(categoryGateway, times(1)).findAllByActiveTrue();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem categorias ativas")
    void deveRetornarListaVazia() {
        // Arrange
        when(categoryGateway.findAllByActiveTrue()).thenReturn(Collections.emptyList());

        // Act
        List<CategoryDomain> result = getAllCategoryUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryGateway, times(1)).findAllByActiveTrue();
    }
}

