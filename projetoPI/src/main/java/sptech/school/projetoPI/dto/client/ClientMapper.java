package sptech.school.projetoPI.dto.client;

import sptech.school.projetoPI.entities.Client;

public class ClientMapper {
    public static Client toEntity(ClientRequestDto requestObject) {
        if(requestObject == null) return null;

        return Client.builder()
                .name(requestObject.getName())
                .cpf(requestObject.getCpf())
                .email(requestObject.getEmail())
                .password(requestObject.getPassword())
                .cep(requestObject.getCep())
                .phone(requestObject.getPhone())
                .active(true)
                .build();
    }

    public static ClientResponseDto toResponseDto(Client entity) {
        if(entity == null) return null;

        return ClientResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .cep(entity.getCep())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ClientResumeResponseDto toResumeResponseDto(Client entity) {
        if(entity == null) return null;

        return ClientResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Client of(ClientLoginDto usuarioLoginDto) {
        Client usuario = new Client();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setPassword(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static ClientTokenDto of(Client usuario, String token) {
        ClientTokenDto usuarioTokenDto = new ClientTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getName());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}