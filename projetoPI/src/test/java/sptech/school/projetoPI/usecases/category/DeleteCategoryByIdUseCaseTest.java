package sptech.school.projetoPI.usecases.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.category.DeleteCategoryByIdUseCase;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryByIdUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private ServiceGateway serviceGateway;

    @InjectMocks
    private DeleteCategoryByIdUseCase deleteCategoryByIdUseCase;

    @Test
    @DisplayName("Deve inativar categoria com sucesso quando ID é válido")
    void deveInativarCategoriaComSucesso() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Teste", "Descrição");

        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(serviceGateway.existsByCategoryId(id)).thenReturn(false);
        when(categoryGateway.findById(id)).thenReturn(Optional.of(category));
        when(categoryGateway.save(any(CategoryDomain.class))).thenReturn(category);

        // Act
        assertDoesNotThrow(() -> deleteCategoryByIdUseCase.execute(id));

        // Assert
        assertFalse(category.getActive());
        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, times(1)).existsByCategoryId(id);
        verify(categoryGateway, times(1)).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando categoria não existe")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        // Arrange
        Integer id = 999;
        when(categoryGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            deleteCategoryByIdUseCase.execute(id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando categoria já está inativa")
    void deveLancarExcecaoQuandoCategoriaJaEstaInativa() {
        // Arrange
        Integer id = 1;
        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            deleteCategoryByIdUseCase.execute(id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }

    @Test
    @DisplayName("Deve lançar ForeignKeyConstraintException quando categoria possui serviços relacionados")
    void deveLancarExcecaoQuandoCategoriaPossuiServicosRelacionados() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Teste", "Descrição");
        ServiceDomain service = new ServiceDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço", 100.0, 60, "Descrição", "imagem.jpg", category);

        when(categoryGateway.existsById(id)).thenReturn(true);
        when(categoryGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(serviceGateway.existsByCategoryId(id)).thenReturn(true);
        when(serviceGateway.findAllByCategoryId(id)).thenReturn(Arrays.asList(service));

        // Act & Assert
        assertThrows(ForeignKeyConstraintException.class, () -> {
            deleteCategoryByIdUseCase.execute(id);
        });

        verify(categoryGateway, times(1)).existsById(id);
        verify(categoryGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, times(1)).existsByCategoryId(id);
        verify(categoryGateway, never()).save(any(CategoryDomain.class));
    }
}

