package sptech.school.projetoPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ScheduleAlreadyRated extends RuntimeException {
    public ScheduleAlreadyRated(String message) {
        super(message);
    }
}
