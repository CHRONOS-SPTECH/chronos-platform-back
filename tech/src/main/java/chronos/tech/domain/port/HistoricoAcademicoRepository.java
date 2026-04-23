package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.HistoricoAcademico;

import java.util.List;
import java.util.Optional;

public interface HistoricoAcademicoRepository {
    List<HistoricoAcademico> findAll();
    Optional<HistoricoAcademico> findById(Long id);
    HistoricoAcademico save(HistoricoAcademico historico);
    void deleteById(Long id);
}
