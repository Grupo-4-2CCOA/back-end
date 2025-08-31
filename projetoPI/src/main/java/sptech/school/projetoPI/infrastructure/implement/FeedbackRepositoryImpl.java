package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.infrastructure.mappers.FeedbackMapper;
import sptech.school.projetoPI.infrastructure.persistence.FeedbackJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaFeedbackRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackGateway {

    private final JpaFeedbackRepository repository;

    @Override
    public Feedback save(Feedback feedback) {
        FeedbackJpaEntity jpaEntity = FeedbackMapper.toJpaEntity(feedback);
        FeedbackJpaEntity savedEntity = repository.save(jpaEntity);
        return FeedbackMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Feedback deleteById(Integer id) {
        // A deleção direta geralmente não retorna um objeto,
        // mas é possível buscar antes e deletar
        Optional<FeedbackJpaEntity> feedback = repository.findById(id);
        feedback.ifPresent(repository::delete);
        return FeedbackMapper.toDomain(feedback.orElse(null));
    }

    @Override
    public boolean existsByClientId(Integer clientId) {
        return repository.existsByClientId(clientId);
    }

    @Override
    public List<Feedback> findAllByClientId(Integer clientId) {
        return repository.findAllByClientId(clientId).stream()
                .map(FeedbackMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Feedback> findById(Integer id) {
        return repository.findById(id).map(FeedbackMapper::toDomain);
    }

    @Override
    public List<Feedback> findAll() {
        return repository.findAll().stream()
                .map(FeedbackMapper::toDomain)
                .collect(Collectors.toList());
    }
}