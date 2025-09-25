package sptech.school.projetoPI.refactor.infraestructure.web.dto.user;


import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;

public record CreateClientRequestDto(String name, String cpf, String email, String phone, String cep, RoleDomain roleDomain) {
}
