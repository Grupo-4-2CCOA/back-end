package sptech.school.projetoPI.core.gateways;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.ServiceDomain;

import java.util.List;
import java.util.Optional;

public interface ServiceGateway {
    ServiceDomain save(ServiceDomain service);
    boolean existsById(Integer id);
    List<ServiceDomain> findAll();
    Optional<ServiceDomain> findById(Integer id);
    ServiceDomain deleteById(Integer id);
    boolean existsByName(String name);
    boolean existsByCategoryId(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<ServiceDomain> findByIdAndActiveTrue(Integer id);
    List<ServiceDomain> findAllByCategoryId(Integer categoryId);
    Page<ServiceDomain> findAllByActiveTrue(Pageable pageable);
}
