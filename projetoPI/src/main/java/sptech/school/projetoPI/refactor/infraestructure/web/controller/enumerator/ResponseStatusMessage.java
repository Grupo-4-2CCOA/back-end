package sptech.school.projetoPI.refactor.infraestructure.web.controller.enumerator;

import lombok.Getter;

@Getter
public enum ResponseStatusMessage {
    POST_SUCCESSFULLY("[201] POST: Entidade cadastrada"),
    POST_CPF_CONFLICT("[409] POST: Já existe usuário com o CPF %s"),
    POST_EMAIL_CONFLICT("[409] POST: Já existe usuário com o Email %s"),
    POST_PHONE_CONFLICT("[409] POST: Já existe usuário com o Telefone %s"),
    POST_ROLE_CONFLICT("[409] POST: Já existe usuário com cargo 'OWNER'"),
    POST_NAME_CONFLICT("[409] POST: Entidade de nome %s já existe"),
    POST_INVALID_TIME_RANGE("[400] POST: Intervalo de tempo inválido"),
    POST_TIME_RANGE_CONFLICT("[409] POST: Funcionário já possui este horário"),
    POST_SCHEDULE_CONFLICT("[409] POST: Agendamento já marcado para este horário"),

    GET_ALL_SUCCESSFULLY("[200] GET: Retornando lista de entidades"),
    GET_ALL_SUCCESSFULLY_EMPTY("[204] GET: [EMPTY] Retornando lista de entidades"),
    GET_BY_ID_SUCCESSFULLY("[200] GET: Entidade de ID %d encontrada"),
    GET_BY_ID_NOT_FOUND("[404] GET: Entidade de ID %d não encontrada"),

    PUT_SUCCESSFULLY("[200] PUT: Entidade de ID %d atualizada"),
    PUT_CPF_CONFLICT("[409] PUT: Já existe usuário com o CPF %s"),
    PUT_EMAIL_CONFLICT("[409] PUT: Já existe usuário com o Email %s"),
    PUT_PHONE_CONFLICT("[409] PUT: Já existe usuário com o Telefone %s"),
    PUT_ROLE_CONFLICT("[409] PUT: Já existe usuário com cargo 'OWNER'"),
    PUT_NOT_FOUND("[404] PUT: Entidade de ID %d não encontrada"),
    PUT_INACTIVE_ENTITY("[409] PUT: Entidade de ID %d está inativada"),
    PUT_NAME_CONFLICT("[409] PUT: Entidade de nome %s já existe"),
    PUT_INVALID_TIME_RANGE("[400] PUT: Intervalo de tempo inválido"),
    PUT_TIME_RANGE_CONFLICT("[409] PUT: Funcionário já possui este horário"),
    PUT_SCHEDULE_CONFLICT("[409] PUT: Agendamento já marcado para este horário"),

    DELETE_SUCCESSFULLY("[204] DELETE: Entidade de ID %d excluída"),
    SOFT_DELETE_SUCCESSFULLY("[204] DELETE: Entidade de ID %d inativada"),
    DELETE_NOT_FOUND("[404] DELETE: Entidade de ID %d não encontrada"),
    DELETE_INACTIVE_ENTITY("[409] DELETE: Entidade de ID %d já está inativada"),
    DELETE_FOREIGN_KEY_CONFLICT("[409] DELETE: Existem %s relacionados com este %s"),

    VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND("[404] VALIDATE REQUEST BODY: %s de ID %d não encontrado");

    private final String message;

    ResponseStatusMessage(String message) {
        this.message = message;
    }
}
