package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.ScheduleDomain;

public interface CalendarGateway {
    void createEventForUser(ScheduleDomain schedule) throws Exception;
    void createEventForSalon(ScheduleDomain schedule) throws Exception;
}
