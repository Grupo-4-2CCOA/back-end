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
            DateTimeFormatter.ISO_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
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
                } catch (Exception ignored) { }
            }

            throw new IllegalArgumentException("Formato de data não suportado: " + source);
        }
    }
}

