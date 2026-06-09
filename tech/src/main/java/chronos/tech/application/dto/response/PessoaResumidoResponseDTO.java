package chronos.tech.application.dto.response;

public record PessoaResumidoResponseDTO(
        Integer id_pessoa,
        String nome,
        String url_foto_perfil
) {}