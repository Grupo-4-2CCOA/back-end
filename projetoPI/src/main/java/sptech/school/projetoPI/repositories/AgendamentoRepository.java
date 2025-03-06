package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
}
