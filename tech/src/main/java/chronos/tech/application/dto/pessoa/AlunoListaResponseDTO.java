package chronos.tech.application.dto.pessoa;

import java.time.LocalDate;
import java.util.List;

public record AlunoListaResponseDTO(
        Integer id_pessoa,
        String nome,
        String email,
        String telefone,
        Double percentual_presenca,
        List<TurmaAtualDTO> turmas_atuais,
        Boolean bolsista,
        String url_foto_perfil,
        String tipo_vinculo_nome,
        Integer tipo_vinculo_id,
        LocalDate data_nascimento,
        LocalDate data_ingresso,
        LocalDate data_membro,
        LocalDate data_saida
) {
    public record TurmaAtualDTO(Integer id_turma, String nome_turma) {}
}
