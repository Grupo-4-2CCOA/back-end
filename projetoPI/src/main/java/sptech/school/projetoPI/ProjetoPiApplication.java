package sptech.school.projetoPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ProjetoPiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPiApplication.class, args);
	}

}
