package chronos.tech.application.dto.response;

public record AlunoComPresencaResponseDTO(
        Integer id_pessoa,
        String nome,
        String cpf,
        String genero,
        String email,
        Double percentual_presenca
) {
}
