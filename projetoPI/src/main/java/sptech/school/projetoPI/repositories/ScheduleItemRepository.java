package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.ScheduleItem;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Integer> {
}
