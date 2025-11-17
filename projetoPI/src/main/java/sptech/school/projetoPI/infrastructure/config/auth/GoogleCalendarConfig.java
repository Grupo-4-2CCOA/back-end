package sptech.school.projetoPI.infrastructure.config.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "google.calendar")
public class GoogleCalendarConfig {
    private String applicationName;
    private SalonConfig salon;

    @Data
    public static class SalonConfig {
        private String calendarId;
        private String credentialsFile;
    }
}