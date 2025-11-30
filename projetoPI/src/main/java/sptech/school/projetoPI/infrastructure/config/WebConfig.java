package sptech.school.projetoPI.infrastructure.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, LocalDateTime.class, new StringToLocalDateTimeConverter());
    }

    public static class StringToLocalDateTimeConverter implements org.springframework.core.convert.converter.Converter<String, LocalDateTime> {

        private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_DATE_TIME,                    // 2025-12-01T15:00:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"), // 2025-12-01T15:00:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),   // 2025-12-01 15:00:00
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),   // 01/12/2025 15:00:00
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),            // 01/12/2025
            DateTimeFormatter.ofPattern("yyyy-MM-dd")             // 2025-12-01
        };

        @Override
        public LocalDateTime convert(String source) {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }

            source = source.trim();

            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    return LocalDateTime.parse(source, formatter);
                } catch (Exception e) {
                    // Tenta o próximo formato
                }
            }

            throw new IllegalArgumentException("Formato de data não suportado: " + source);
        }
    }
}

