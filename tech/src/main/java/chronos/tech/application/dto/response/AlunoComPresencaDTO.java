package chronos.tech.application.dto.response;

public record AlunoComPresencaDTO(
        Integer id_pessoa,
        String nome,
        String cpf,
        Double percentual_presenca
) {
}
