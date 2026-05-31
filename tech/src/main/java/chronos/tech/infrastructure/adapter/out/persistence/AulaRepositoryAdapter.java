package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.port.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AulaRepositoryAdapter implements AulaRepository {
    private final SpringDataAulaRepository repository;

    @Override
    public List<Aula> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Aula> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Aula save(Aula aula) {
        return repository.save(aula);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Aula> findByDataAula(Date data) {
        return repository.findByDataAula(data);
    }

    @Override
    public List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId) {
        return repository.findByDataAulaAndInstrutorIdPessoa(data, instrutorId);
    }

    @Override
    public List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula) {
        return repository.findByInstrutorIdPessoaAndDataAula(idInstrutor, dataAula);
    }

    public List<Aula> findByTurmaIdTurma(Integer idTurma){
        return repository.findByTurmaIdTurma(idTurma);
    }

    @Override
    public List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula) {
        return repository.findByTurmaIdTurmaAndDataAula(idTurma, dataAula);
    }
}