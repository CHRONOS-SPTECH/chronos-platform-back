package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.port.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AulaRepositoryAdapter implements AulaRepository {
    private final SpringDataAulaRepository repository;

    public List<Aula> findAll() {
        return repository.findAll();
    }

    public Optional<Aula> findById(Integer id) {
        return repository.findById(id);
    }

    public Aula save(Aula aula) {
        return repository.save(aula);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<Aula> findByDataAula(Date data) {
        return repository.findByDataAula(data);
    }

    public List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId) {
        return repository.findByDataAulaAndInstrutorIdPessoa(data, instrutorId);
    }
}
