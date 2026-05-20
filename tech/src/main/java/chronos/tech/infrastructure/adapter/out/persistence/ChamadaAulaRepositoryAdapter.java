package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.port.ChamadaAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChamadaAulaRepositoryAdapter implements ChamadaAulaRepository {
    private final SpringDataChamadaAulaRepository repository;

    @Override
    public List<ChamadaAula> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ChamadaAula> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ChamadaAula save(ChamadaAula chamadaAula) {
        return repository.save(chamadaAula);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ChamadaAula> findByPessoaIdPessoaAndAulaTurmaIdTurma(Integer pessoaId, Integer turmaId) {
        return repository.findByPessoaIdPessoaAndAulaTurmaIdTurma(pessoaId, turmaId);
    }
}
