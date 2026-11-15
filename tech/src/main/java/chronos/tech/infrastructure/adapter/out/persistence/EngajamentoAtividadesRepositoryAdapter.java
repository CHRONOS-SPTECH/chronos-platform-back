package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.port.EngajamentoAtividadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EngajamentoAtividadesRepositoryAdapter implements EngajamentoAtividadesRepository {

    private final SpringDataEngajamentoAtividadesRepository repository;

    @Override
    public List<EventoParticipacaoResumo> findResumoEventosRealizadosAte(LocalDate data) {
        return repository.findResumoEventosRealizadosAte(data).stream()
                .map(row -> new EventoParticipacaoResumo(
                        toInteger(row[0]),
                        (String) row[1],
                        (LocalDate) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        toLong(row[6]),
                        toLong(row[7])
                ))
                .toList();
    }

    @Override
    public List<PessoaParticipanteResumo> findParticipantesPresentesAte(LocalDate data) {
        return repository.findParticipantesPresentesAte(data).stream()
                .map(row -> new PessoaParticipanteResumo(
                        toInteger(row[0]),
                        (String) row[1],
                        (LocalDate) row[2]
                ))
                .toList();
    }

    @Override
    public Map<Integer, LocalDate> findPrimeiraMatriculaPorPessoa() {
        Map<Integer, LocalDate> resultado = new HashMap<>();
        for (Object[] row : repository.findPrimeiraMatriculaPorPessoa()) {
            resultado.put(toInteger(row[0]), (LocalDate) row[1]);
        }
        return resultado;
    }

    // COUNT/SUM podem voltar como Long, Integer ou BigDecimal conforme o dialeto.
    private static long toLong(Object valor) {
        return valor == null ? 0L : ((Number) valor).longValue();
    }

    private static Integer toInteger(Object valor) {
        return valor == null ? null : ((Number) valor).intValue();
    }
}
