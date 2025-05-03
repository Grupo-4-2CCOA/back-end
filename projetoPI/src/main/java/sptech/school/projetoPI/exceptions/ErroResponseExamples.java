package sptech.school.projetoPI.exceptions;

public class ErroResponseExamples {
        public static final String NOT_FOUND = """
        {
          "status": 404,
          "erro": "Recurso não encontrado"
        }
        """;

        public static final String UNAUTHORIZED = """
        {
          "status": 401,
          "erro": "Não autorizado",
          "mensagem": "Token de autenticação inválido ou ausente"
        }
        """;

        public static final String FORBIDDEN = """
        {
          "status": 403,
          "erro": "Acesso proibido",
          "mensagem": "Você não tem permissão para acessar este recurso"
        }
        """;

        public static final String NO_CONTENT = """
        {
          "status": 204,
          "mensagem": "Requisição bem-sucedida, mas sem conteúdo a retornar"
        }
        """;

        public static final String BAD_REQUEST = """
        {
          "status": 400,
          "erro": "Requisição inválida",
          "mensagem": "Verifique os dados enviados"
        }
        """;

        public static final String CREATED = """
        {
          "status": 201,
          "mensagem": "Recurso criado com sucesso"
        }
        """;

        public static final String OK = """
        {
          "status": 200,
          "mensagem": "Requisição realizada com sucesso"
        }
        """;
    }

