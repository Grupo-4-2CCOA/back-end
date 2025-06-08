package sptech.school.projetoPI.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {
    abstract void executeEntitySignWithValidParametersTest();
    abstract void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest();
    abstract void executeEntityFindByIdMustReturnEntityWithIdOneTest();
    abstract void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest();
    abstract void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest();
    abstract void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest();
    abstract void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest();
    abstract void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest();
}
