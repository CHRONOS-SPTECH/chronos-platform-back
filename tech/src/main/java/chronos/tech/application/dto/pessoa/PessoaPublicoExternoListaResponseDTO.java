package chronos.tech.application.dto.pessoa;

public record PessoaPublicoExternoListaResponseDTO(
        Integer id_pessoa,
        String nome,
        String email,
        String telefone,
        String cpf,
        Integer tipo_vinculo_id,
        String tipo_vinculo_nome
) {}
