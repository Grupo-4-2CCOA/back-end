package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    boolean existsByType(String type);
    boolean existsByCategoryId(Integer id);
    List<Service> findAllByCategoryId(Integer id);
}
