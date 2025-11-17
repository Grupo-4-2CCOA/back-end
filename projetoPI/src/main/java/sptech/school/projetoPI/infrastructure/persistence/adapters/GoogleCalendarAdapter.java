package sptech.school.projetoPI.infrastructure.persistence.adapters;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.client.RestTemplate;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.CalendarGateway;
import sptech.school.projetoPI.infrastructure.config.auth.GoogleCalendarConfig;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCalendarAdapter implements CalendarGateway {
    private final GoogleCalendarConfig googleCalendarConfig;
    private final ResourceLoader resourceLoader;

    private Calendar getSalonClient() throws Exception {
        try {
            Resource credentialsResource = resourceLoader.getResource(googleCalendarConfig.getSalon().getCredentialsFile());

            if (!credentialsResource.exists()) {
                throw new IllegalStateException("Arquivo de credenciais não encontrado: " +
                        googleCalendarConfig.getSalon().getCredentialsFile());
            }

            GoogleCredential credential = GoogleCredential
                    .fromStream(credentialsResource.getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/calendar"));

            log.debug("Criando cliente do Google Calendar para o salão");

            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
            )
                    .setApplicationName(googleCalendarConfig.getApplicationName())
                    .build();

        } catch (Exception e) {
            log.error("Erro ao criar cliente do Google Calendar para o salão", e);
            throw new Exception("Falha ao conectar com Google Calendar do salão: " + e.getMessage(), e);
        }
    }

    private Calendar getUserClient(String googleAccessToken) throws Exception {
        try {
            if (googleAccessToken == null || googleAccessToken.trim().isEmpty()) {
                throw new IllegalStateException("Token do Google não fornecido");
            }

            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    request -> request.getHeaders().setAuthorization("Bearer " + googleAccessToken)
            )
                    .setApplicationName(googleCalendarConfig.getApplicationName())
                    .build();

        } catch (Exception e) {
            throw new Exception("Falha ao conectar com Google Calendar do usuário: " + e.getMessage(), e);
        }
    }

    private String getGoogleAccessTokenFromHeader() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.warn("❌ RequestAttributes é null");
                return null;
            }

            HttpServletRequest request = attributes.getRequest();
            String googleToken = request.getHeader("X-Google-Access-Token");

            if (googleToken != null && !googleToken.trim().isEmpty()) {
                log.info("📨 Token do Google encontrado no header. Comprimento: {}", googleToken.length());
                return googleToken;
            } else {
                log.warn("⚠️ Header X-Google-Access-Token não encontrado ou vazio");

                java.util.Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                }
            }

            return null;
        } catch (Exception e) {
            log.warn("❌ Não foi possível obter token do Google do header: {}", e.getMessage());
            return null;
        }
    }

    private Event buildEvent(ScheduleDomain schedule) {
        try {
            Date startDate = Date.from(schedule.getAppointmentDatetime()
                    .atZone(ZoneId.systemDefault()).toInstant());

            Date endDate = Date.from(schedule.getAppointmentDatetime()
                    .plusMinutes(schedule.getDuration())
                    .atZone(ZoneId.systemDefault()).toInstant());

            String services = schedule.getItems().stream()
                    .map(i -> Optional.ofNullable(i.getService())
                            .map(service -> "• " + service.getName())
                            .orElse("• Serviço não disponível"))
                    .reduce("", (a, b) -> a + "\n" + b);

            String clientName = schedule.getClientDomain().getName();
            String employeeName = schedule.getEmployeeDomain().getName();

            String description = String.format(
                    "💈 Agendamento Beauty Barreto\n\n" +
                            "👤 Cliente: %s\n" +
                            "💇 Profissional: %s\n" +
                            "⏱️ Duração: %d minutos\n\n" +
                            "📋 Serviços:\n%s\n\n" +
                            "📞 Contato: Verificar sistema",
                    clientName, employeeName, schedule.getDuration(), services
            );

            return new Event()
                    .setSummary("💈 " + clientName + " - Beauty Barreto")
                    .setDescription(description)
                    .setStart(new EventDateTime().setDateTime(new DateTime(startDate)))
                    .setEnd(new EventDateTime().setDateTime(new DateTime(endDate)));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao construir evento do calendário", e);
        }
    }

    @Override
    public void createEventForUser(ScheduleDomain schedule) throws Exception {
        Calendar userCalendar = null;
        try {
            String googleAccessToken = getGoogleAccessTokenFromHeader();

            if (googleAccessToken == null) {
                log.warn("Token do Google não encontrado no header. Pulando criação no calendário do usuário.");
                return;
            }

            try {
                String validationUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + googleAccessToken;
                RestTemplate restTemplate = new RestTemplate();
                String validationResult = restTemplate.getForObject(validationUrl, String.class);
                log.info("Token válido segundo Google: {}", validationResult);
            } catch (Exception e) {
                log.error("Token inválido ou expirado: {}", e.getMessage());
                throw new Exception("Token do Google inválido ou expirado: " + e.getMessage());
            }

            userCalendar = getUserClient(googleAccessToken);
            Event event = buildEvent(schedule);

            Event createdEvent = userCalendar.events()
                    .insert("primary", event)
                    .execute();

            log.info("EVENTO CRIADO NO CALENDÁRIO DO USUÁRIO: {} - {}",
                    createdEvent.getId(), createdEvent.getSummary());
            log.info("🔗 Link: {}", createdEvent.getHtmlLink());

        } catch (Exception e) {
            log.error("Erro ao criar evento no calendário do usuário: {}", schedule.getId(), e);
            throw new Exception("Falha ao criar evento no calendário do usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void createEventForSalon(ScheduleDomain schedule) throws Exception {
        Calendar salonCalendar = null;
        try {
            salonCalendar = getSalonClient();
            Event event = buildEvent(schedule);

            String salonCalendarId = "beautybarreto01@gmail.com";

            try {
                Event createdEvent = salonCalendar.events()
                        .insert(salonCalendarId, event)
                        .execute();
                log.info("Evento criado no calendário do salão (primary): {} - {}",
                        createdEvent.getId(), createdEvent.getSummary());
            } catch (Exception e) {
                salonCalendarId = googleCalendarConfig.getSalon().getCalendarId();
                Event createdEvent = salonCalendar.events()
                        .insert(salonCalendarId, event)
                        .execute();
                log.info("Evento criado no calendário do salão ({}): {} - {}",
                        salonCalendarId, createdEvent.getId(), createdEvent.getSummary());
            }

        } catch (Exception e) {
            log.error("Erro ao criar evento no calendário do salão para agendamento: {}", schedule.getId(), e);
            throw new Exception("Falha ao criar evento no calendário do salão: " + e.getMessage(), e);
        }
    }
}