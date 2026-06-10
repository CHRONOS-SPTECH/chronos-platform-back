package chronos.tech.application.service;

import chronos.tech.application.dto.response.dashboard.*;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.HistoricoAcademicoRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PessoaRepository pessoaRepository;
    private final TurmaRepository turmaRepository;
    private final HistoricoAcademicoRepository historicoRepository;
    private final AulaRepository aulaRepository;

    public DashboardResumoResponseDTO getResumo() {
        Long total       = pessoaRepository.countTotal();
        Long membros     = pessoaRepository.countByTipoVinculo("Membro");
        Long membro_forca = pessoaRepository.countByTipoVinculo("Membro Força Viva");
        Long provac      = pessoaRepository.countByTipoVinculo("Provacionista");
        Long externo     = pessoaRepository.countByTipoVinculo("Público Externo");
        Long instrutores = (long) aulaRepository.findInstrutoresAtivos().size();
        Long membrosAtivos = pessoaRepository.countMembrosAtivos();
        Long percentual  = membros > 0 ? (membrosAtivos * 100) / membros : 0L;
        Long emAndamento = turmaRepository.countByStatus(StatusTurma.EM_ANDAMENTO);
        Long naoIniciadas = turmaRepository.countByStatus(StatusTurma.NAO_INICIADA);

        return new DashboardResumoResponseDTO(
                new DashboardResumoResponseDTO.ComunidadeDTO(total, membros + membro_forca, provac, externo),
                new DashboardResumoResponseDTO.CapacidadeDTO(instrutores),
                new DashboardResumoResponseDTO.EngajamentoDTO(membrosAtivos, percentual),
                new DashboardResumoResponseDTO.ResumoTurmasDTO(emAndamento, naoIniciadas)
        );
    }

    public DashboardInstrutoresResponseDTO getInstrutores() {
        List<Pessoa> lista = aulaRepository.findInstrutoresAtivos();
        List<DashboardInstrutoresResponseDTO.InstrutorDTO> instrutores = lista.stream()
                .map(p -> new DashboardInstrutoresResponseDTO.InstrutorDTO(
                        p.getIdPessoa().longValue(),
                        p.getNome(),
                        p.getNome() != null && !p.getNome().isBlank()
                                ? String.valueOf(p.getNome().charAt(0)).toUpperCase() : "",
                        p.getUrlFotoPerfil(),
                        p.getDataSaida() == null
                ))
                .toList();

        return new DashboardInstrutoresResponseDTO(instrutores.size(), instrutores);
    }

    public List<DashboardNivelResponseDTO> getAlunosPorNivel() {
        List<Object[]> raw = historicoRepository.countAlunosPorNivel();
        Map<Integer, Long> porNivel = raw.stream()
                .collect(Collectors.toMap(
                        r -> (Integer) r[0],
                        r -> (Long) r[1]
                ));

        // Garante todos os níveis de 1 a 5, mesmo sem alunos
        return IntStream.rangeClosed(1, 5)
                .mapToObj(n -> new DashboardNivelResponseDTO(n, porNivel.getOrDefault(n, 0L)))
                .toList();
    }

    public DashboardGeneroResponseDTO getGenero() {
        List<Object[]> raw = pessoaRepository.countByGenero();
        Long total = pessoaRepository.countTotal();

        List<DashboardGeneroResponseDTO.GeneroDTO> distribuicao = new ArrayList<>();
        long somaPercentuais = 0;
        DashboardGeneroResponseDTO.GeneroDTO maior = null;

        for (Object[] row : raw) {
            String genero = (String) row[0];
            Long qtd = (Long) row[1];
            long percentual = total > 0 ? (qtd * 100) / total : 0;
            somaPercentuais += percentual;
            DashboardGeneroResponseDTO.GeneroDTO dto = new DashboardGeneroResponseDTO.GeneroDTO(genero, qtd, percentual);
            distribuicao.add(dto);
            if (maior == null || qtd > maior.total()) maior = dto;
        }

        // Ajuste para garantir que percentuais somem 100
        if (maior != null && somaPercentuais != 100) {
            long diff = 100 - somaPercentuais;
            DashboardGeneroResponseDTO.GeneroDTO finalMaior = maior;
            distribuicao = distribuicao.stream()
                    .map(d -> d.genero().equals(finalMaior.genero())
                            ? new DashboardGeneroResponseDTO.GeneroDTO(d.genero(), d.total(), d.percentual() + diff)
                            : d)
                    .toList();
            maior = new DashboardGeneroResponseDTO.GeneroDTO(maior.genero(), maior.total(), maior.percentual() + diff);
        }

        String nota = "A base apresenta predominância " + (maior != null ? maior.genero() : "")
                + " de " + (maior != null ? maior.percentual() : 0) + "%";

        DashboardGeneroResponseDTO.EquilibrioDTO equilibrio = new DashboardGeneroResponseDTO.EquilibrioDTO(
                maior != null ? maior.genero() : "",
                maior != null ? maior.percentual() : 0L,
                nota
        );

        return new DashboardGeneroResponseDTO(distribuicao, equilibrio);
    }

    public DashboardFaixaEtariaResponseDTO getFaixaEtaria() {
        List<Object[]> dados = pessoaRepository.findDatasNascimentoEGenero();
        LocalDate hoje = LocalDate.now();

        Map<String, long[]> faixas = new LinkedHashMap<>();
        faixas.put("18-24", new long[]{0, 0}); // [mulheres, homens]
        faixas.put("25-34", new long[]{0, 0});
        faixas.put("35-44", new long[]{0, 0});
        faixas.put("45-54", new long[]{0, 0});
        faixas.put("55-64", new long[]{0, 0});
        faixas.put("65+",   new long[]{0, 0});

        long somaIdades = 0;
        int count = 0;

        for (Object[] row : dados) {
            LocalDate nascimento = (LocalDate) row[0];
            String genero = (String) row[1];
            int idade = (int) ChronoUnit.YEARS.between(nascimento, hoje);
            somaIdades += idade;
            count++;

            String faixa = getFaixa(idade);
            if (faixa == null) continue;

            boolean feminino = genero != null && genero.toLowerCase().contains("femin");
            if (feminino) faixas.get(faixa)[0]++;
            else          faixas.get(faixa)[1]++;
        }

        List<DashboardFaixaEtariaResponseDTO.FaixaDTO> lista = faixas.entrySet().stream()
                .map(e -> new DashboardFaixaEtariaResponseDTO.FaixaDTO(e.getKey(), e.getValue()[0], e.getValue()[1]))
                .toList();

        String maisComum = faixas.entrySet().stream()
                .max(Comparator.comparingLong(e -> e.getValue()[0] + e.getValue()[1]))
                .map(Map.Entry::getKey).orElse("N/A");

        long[] acima65 = faixas.get("65+");
        long totalAcima65 = acima65[0] + acima65[1];
        int media = count > 0 ? (int) (somaIdades / count) : 0;

        return new DashboardFaixaEtariaResponseDTO(
                lista,
                new DashboardFaixaEtariaResponseDTO.ResumoFaixaDTO(maisComum, media, totalAcima65)
        );
    }

    private String getFaixa(int idade) {
        if (idade >= 18 && idade <= 24) return "18-24";
        if (idade >= 25 && idade <= 34) return "25-34";
        if (idade >= 35 && idade <= 44) return "35-44";
        if (idade >= 45 && idade <= 54) return "45-54";
        if (idade >= 55 && idade <= 64) return "55-64";
        if (idade >= 65)                return "65+";
        return null;
    }
}