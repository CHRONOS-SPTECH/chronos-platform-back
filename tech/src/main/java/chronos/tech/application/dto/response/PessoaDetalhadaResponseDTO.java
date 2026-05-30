package chronos.tech.application.dto.response;

import java.time.LocalDate;

public record PessoaDetalhadaResponseDTO(
        Integer id_pessoa,
        String nome,
        String email,
        String telefone,
        String genero,
        String cpf,
        Boolean bolsista,
        String url_foto_perfil,
        LocalDate data_nascimento,
        LocalDate data_ingresso,
        LocalDate data_membro,
        LocalDate data_saida,
        TipoVinculoResponseDTO vinculo
) {
}