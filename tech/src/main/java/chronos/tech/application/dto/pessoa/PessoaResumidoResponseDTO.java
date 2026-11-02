package chronos.tech.application.dto.pessoa;

public record PessoaResumidoResponseDTO(
        Integer id_pessoa,
        String nome,
        String url_foto_perfil
) {}