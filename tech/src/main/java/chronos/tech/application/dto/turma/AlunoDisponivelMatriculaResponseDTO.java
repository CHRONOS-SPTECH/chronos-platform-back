package chronos.tech.application.dto.turma;

public record AlunoDisponivelMatriculaResponseDTO(
        Long id_pessoa,
        String nome,
        String email,
        String telefone,
        String tipo_vinculo
) {}
