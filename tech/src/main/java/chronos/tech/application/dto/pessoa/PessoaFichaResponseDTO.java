package chronos.tech.application.dto.pessoa;

import chronos.tech.application.dto.cadastro.TipoVinculoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record PessoaFichaResponseDTO(
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
        TipoVinculoResponseDTO vinculo,
        EnderecoPessoaResponseDTO endereco,
        boolean possui_biometria,
        List<TurmaPessoaDetalheResponseDTO> turmas,
        long total_chamadas,
        long presencas,
        double percentual_presenca,
        List<HistoricoAcademicoDetalheResponseDTO> historico_academico
) {
}
