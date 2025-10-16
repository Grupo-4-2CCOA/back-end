package sptech.school.projetoPI.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitMQconfig {
    public static final String QUEUE_NAME = "Schedule";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}
