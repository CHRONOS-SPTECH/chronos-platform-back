package chronos.tech.application.dto.response;

public record ItemRelatorioImportacaoResponseDTO(
    int linha,            // Número da linha no Excel para o usuário se localizar
    String turma,         // Nome da turma que estava na planilha
    String professor,     // Nome do professor que estava na planilha
    String materia,       // Nome da matéria
    String motivoErro     // Ex: "Turma não encontrada", "Conflito de horário com Prof. Platão"
) {}