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
    public List<Aula> findAll() { return repository.findAll(); }
    public Optional<Aula> findById(Integer id) { return repository.findById(id); }
    public Aula save(Aula aula) { return repository.save(aula); }
    public void deleteById(Integer id) { repository.deleteById(id); }

    @Override
    public List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula) {
        return repository.findByInstrutorIdPessoaAndDataAula(idInstrutor, dataAula);
    }

    @Override
    public List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula) {
        return repository.findByTurmaIdTurmaAndDataAula(idTurma, dataAula);
    }
}
