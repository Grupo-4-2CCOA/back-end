package sptech.school.projetoPI.core.gateways;

public interface FileUploadGateway {
    String uploadFile(String fileName, byte[] fileContent);
    String getFileUrl(String fileName);
}
