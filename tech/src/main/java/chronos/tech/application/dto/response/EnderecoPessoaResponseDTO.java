package chronos.tech.application.dto.response;

public record EnderecoPessoaResponseDTO(
        Integer id_endereco,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        Integer id_pessoa
) {
}
