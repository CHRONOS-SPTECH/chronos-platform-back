package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.port.PerfilAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PerfilAcessoRepositoryAdapter implements PerfilAcessoRepository {

    private final SpringDataPerfilAcessoRepository repository;

    @Override
    public List<PerfilAcesso> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PerfilAcesso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public PerfilAcesso save(PerfilAcesso perfilAcesso) {
        return repository.save(perfilAcesso);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
