package chronos.tech.application.dto.response.dashboard;

public record DashboardResumoResponseDTO(
        ComunidadeDTO comunidade_academica,
        CapacidadeDTO capacidade_pedagogica,
        EngajamentoDTO engajamento_voluntario,
        ResumoTurmasDTO resumo_turmas

) {
    public record ComunidadeDTO(
            Long total_pessoas, Long membros,
            Long provacionistas, Long publico_externo
    ) {}
    public record CapacidadeDTO(Long total_instrutores) {}
    public record EngajamentoDTO(Long membros_ativos, Long percentual_quadro) {}
    public record ResumoTurmasDTO(Long em_andamento, Long nao_iniciadas) {}
}
