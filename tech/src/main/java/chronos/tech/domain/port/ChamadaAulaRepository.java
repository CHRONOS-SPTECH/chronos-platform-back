package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.ChamadaAula;

import java.util.List;
import java.util.Optional;

public interface ChamadaAulaRepository {
    List<ChamadaAula> findAll();
    Optional<ChamadaAula> findById(Long id);
    ChamadaAula save(ChamadaAula chamadaAula);
    void deleteById(Long id);
    List<ChamadaAula> findByPessoaIdPessoaAndAulaTurmaIdTurma(Integer pessoaId, Integer turmaId);

    List<ChamadaAula> saveAll(List<ChamadaAula> chamadas);
}
