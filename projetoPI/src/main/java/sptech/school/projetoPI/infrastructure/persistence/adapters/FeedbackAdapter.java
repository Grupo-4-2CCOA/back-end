package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.infrastructure.mappers.FeedbackMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaFeedbackRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackAdapter implements FeedbackGateway {

    private final JpaFeedbackRepository repository;

    @Override
    public FeedbackDomain save(FeedbackDomain feedbackDomain) {
        FeedbackJpaEntity jpaEntity = FeedbackMapper.toJpaEntity(feedbackDomain);
        FeedbackJpaEntity savedEntity = repository.save(jpaEntity);
        return FeedbackMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public FeedbackDomain deleteById(Integer id) {
        // A deleção direta geralmente não retorna um objeto,
        // mas é possível buscar antes e deletar
        Optional<FeedbackJpaEntity> feedback = repository.findById(id);
        feedback.ifPresent(repository::delete);
        return FeedbackMapper.toDomain(feedback.orElse(null));
    }

    @Override
    public Optional<FeedbackDomain> findById(Integer id) {
        return repository.findById(id).map(FeedbackMapper::toDomain);
    }

    @Override
    public Page<FeedbackDomain> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(FeedbackMapper::toDomain);
    }
}