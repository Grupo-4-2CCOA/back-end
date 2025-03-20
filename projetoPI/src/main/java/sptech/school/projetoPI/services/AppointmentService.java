package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Appointment;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.AppointmentRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, EmployeeRepository employeeRepository, ServiceRepository serviceRepository) {
        this.repository = appointmentRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
    }

    public Appointment signAppointment(Appointment appointment) {
        validateRequestBody(appointment);
        appointment.setId(null);
        return repository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment getAppointmentById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O atendimento de ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Appointment updateAppointmentById(Appointment appointment, Integer id) {
        if(repository.existsByTransactionHashAndIdNot(appointment.getTransactionHash(), id)) {
            throw new EntityConflictException(
                    "Já existe um atendimento com um pagamento cadastrado contendo este código hash: %s".formatted(appointment.getTransactionHash())
            );
        }

        validateRequestBody(appointment);
        appointment.setId(id);
        return repository.save(appointment);
    }

    public ResponseEntity<Void> deleteAppointmentById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O atendimento de ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    // Validação do POST & PUT
    public void validateRequestBody(Appointment appointment) {
        List<String> entidadesNaoEncontradas = new ArrayList<>();

        if(!employeeRepository.existsById(appointment.getEmployee().getId())) entidadesNaoEncontradas.add("Funcionário");
        if(!serviceRepository.existsById(appointment.getService().getId())) entidadesNaoEncontradas.add("Serviço");

        if(!entidadesNaoEncontradas.isEmpty()) {
            throw new EntityNotFoundException(
                    "As seguintes entidades não foram encontrada: " + entidadesNaoEncontradas
            );
        }
    }
}
