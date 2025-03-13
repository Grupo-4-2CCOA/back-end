package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    boolean existsByDescriptionIgnoreCase(String description);
    boolean existsByTypeIgnoreCase(String type);
}
