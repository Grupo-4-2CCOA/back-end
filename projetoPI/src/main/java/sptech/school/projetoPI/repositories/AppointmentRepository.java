package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    boolean existsByServiceId(Integer serviceId);
    boolean existsByEmployeeId(Integer employeeId);
    boolean existsByTransactionHashAndIdNot(String transactionHase, Integer id);
}
