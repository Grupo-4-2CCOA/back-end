package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
