package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    boolean existsByDescricaoIgnoreCase(String descricao);
    boolean existsByTipoIgnoreCase(String tipo);
}
