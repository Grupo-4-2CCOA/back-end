package sptech.school.projetoPI.infrastructure.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;

@Component
public class S3FileUploadGatewayImpl implements FileUploadGateway {
    private final S3Client s3Client;

    @Value("${aws.s3.nome-bucket}")
    private String bucketName;

    public S3FileUploadGatewayImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(String fileName, byte[] fileContent) {
        if (fileContent == null || fileContent.length == 0) {
            throw new ResponseStatusException(400, "O arquivo não pode ser vazio", null);
        }

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));

            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toString();
        } catch (S3Exception exception) {
            throw new ResponseStatusException(500, "Erro ao enviar para o S3: " + exception.getMessage(), exception);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return s3Client.utilities().getUrl(builder ->
                builder.bucket(bucketName).key(fileName)
        ).toString();
    }
}
