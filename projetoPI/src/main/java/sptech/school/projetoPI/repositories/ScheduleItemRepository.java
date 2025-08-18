package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.ScheduleItem;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Integer> { }
