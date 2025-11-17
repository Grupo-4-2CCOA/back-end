package sptech.school.projetoPI.core.gateways;

import com.google.api.services.calendar.model.Event;
import sptech.school.projetoPI.core.domains.ScheduleDomain;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarGateway {
    void createEventForUser(ScheduleDomain schedule) throws Exception;
    void createEventForSalon(ScheduleDomain schedule) throws Exception;
}
