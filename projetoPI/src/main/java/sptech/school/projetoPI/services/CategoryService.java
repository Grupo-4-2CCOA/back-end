package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category> {
    private final CategoryRepository repository;
    private final ServiceRepository serviceRepository;

    @Override
    public Category postMethod(Category category) {
        if(repository.existsByName(category.getName())) {
            log.error(Logs.POST_NAME_CONFLICT.getMessage().formatted(category.getName()));
            throw new EntityConflictException(
                    Logs.POST_NAME_CONFLICT.getMessage().formatted(category.getName())
            );
        }

        category.setId(null);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.POST_SUCCESSFULLY.getMessage());
        return repository.save(category);
    }

    @Override
    public List<Category> getAllMethod() {
        log.info("{} - [INFO] GET: Retornando lista de entidades", LocalTime.now());
        return repository.findAllByActiveTrue();
    }

    @Override
    public Category getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id)
                .map((entity) -> {
                    log.info("{} - [INFO] GET: Retornando entidade de ID {}", LocalTime.now(), id);
                    return entity;
                })
                .orElseThrow(() -> {
                    log.error("{} - [ERROR 404] GET: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
                    return new EntityNotFoundException(
                            "Category de ID %d não foi encontrado".formatted(id)
                    );
                });
    }

    @Override
    public Category putByIdMethod(Category category, Integer id) {
        if (!repository.existsById(id)) {
            log.error("{} - [ERROR 404] PUT: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
            throw new EntityNotFoundException(
                    "Category com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            log.error("{} - [ERROR 400] PUT: Entidade de ID {} já está inativa. Abortar execução", LocalTime.now(), id);
            throw new InactiveEntityException(
                    "Category com o ID %d já está inativa".formatted(id)
            );
        }

        if(repository.existsByIdNotAndName(id, category.getName())) {
            log.error("{} - [ERROR 409] PUT: Entidade de nome {} já existe. Abortar execução", LocalTime.now(), category.getName());
            throw new EntityConflictException(
                    "Category com o mesmo nome já existe na base de dados"
            );
        }

        category.setId(id);
        category.setCreatedAt(repository.findById(id).get().getCreatedAt());
        category.setUpdatedAt(LocalDateTime.now());
        log.info("{} - [INFO] PUT: Entidade de ID {} atualizada", LocalTime.now(), id);
        return repository.save(category);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            log.error("{} - [ERROR 404] DELETE: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
            throw new EntityNotFoundException(
                    "Category com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            log.error("{} - [ERROR 400] DELETE: Entidade de ID {} já está inativa. Abortar execução", LocalTime.now(), id);
            throw new InactiveEntityException(
                    "Category com o ID %d já está inativa".formatted(id)
            );
        }

        if (serviceRepository.existsByCategoryId(id)) {
            log.error("{} - [ERROR 409] DELETE: Entidade de ID {} relacionada com Services. Abortar execução", LocalTime.now(), id);
            throw new ForeignKeyConstraintException(
                    "Os seguintes serviços estão relacionados com esta categoria: %s".formatted(serviceRepository.findAllByCategoryId(id)
                            .stream().map(sptech.school.projetoPI.core.domains.Service::getId).toList())
            );
        }

        Category category = repository.findById(id).get();
        category.setActive(false);
        category.setUpdatedAt(LocalDateTime.now());
        repository.save(category);
    }
}
