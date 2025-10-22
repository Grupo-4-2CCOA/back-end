package sptech.school.projetoPI.infrastructure.di;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  public static final String QUEUE_NAME = "Feedback";

  @Bean
  public Queue queue() {
    return new Queue(QUEUE_NAME, true);
  }
}