package sptech.school.projetoPI.refactor.core.application.command.user;

import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

import java.time.LocalDateTime;

public record CreateUserCommand(Integer id, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Email email, Cpf cpf, Phone phone, Cep cep, RoleDomain roleDomain) {
}
