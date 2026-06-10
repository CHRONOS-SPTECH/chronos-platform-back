package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.HistoricoAcademico;
import chronos.tech.domain.port.HistoricoAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HistoricoAcademicoRepositoryAdapter implements HistoricoAcademicoRepository {
    private final SpringDataHistoricoAcademicoRepository repository;

    @Override
    public List<HistoricoAcademico> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<HistoricoAcademico> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public HistoricoAcademico save(HistoricoAcademico historico) {
        return repository.save(historico);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Object[]> countAlunosPorNivel() {
        return repository.countAlunosPorNivel();
    }
}