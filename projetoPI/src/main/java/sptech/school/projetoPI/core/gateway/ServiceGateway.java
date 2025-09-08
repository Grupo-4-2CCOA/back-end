package sptech.school.projetoPI.core.gateway;

import sptech.school.projetoPI.core.domain.ServiceDomain;

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
    List<ServiceDomain> findAllByActiveTrue();
}
