package chronos.tech.application.dto.response;

import java.util.List;

public record TurmaAlunoPaginaResponseDTO(
        Long id_turma,
        String nome_turma,
        long total_alunos,
        int pagina,
        int tamanho,
        int total_paginas,
        List<TurmaAlunoResponseDTO.AlunoResumoDTO> alunos
) {}
