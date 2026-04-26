package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.port.TipoVinculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoVinculoRepositoryAdapter implements TipoVinculoRepository {
    private final SpringDataTipoVinculoRepository repository;
    public List<TipoVinculo> findAll() { return repository.findAll(); }
    public Optional<TipoVinculo> findById(Integer id) { return repository.findById(id); }
    public TipoVinculo save(TipoVinculo tipoVinculo) { return repository.save(tipoVinculo); }
    public void deleteById(Integer id) { repository.deleteById(id); }
}
