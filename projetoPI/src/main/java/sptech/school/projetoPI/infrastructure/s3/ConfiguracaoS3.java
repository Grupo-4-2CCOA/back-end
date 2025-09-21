package sptech.school.projetoPI.infrastructure.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class ConfiguracaoS3 {
    @Value("${aws.regiao}")
    private String awsRegion;

    @Bean
    public S3Client clienteS3(){
        return  S3Client.builder()
                .region(Region.of(awsRegion))
                .build();
    }
}

