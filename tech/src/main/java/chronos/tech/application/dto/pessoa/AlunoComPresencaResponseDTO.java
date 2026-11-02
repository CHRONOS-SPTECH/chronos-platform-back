package chronos.tech.application.dto.pessoa;

public record AlunoComPresencaResponseDTO(
        Integer id_pessoa,
        String nome,
        String cpf,
        String genero,
        String email,
        Double percentual_presenca
) {
}
