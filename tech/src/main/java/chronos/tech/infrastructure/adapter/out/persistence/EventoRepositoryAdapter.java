package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.port.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventoRepositoryAdapter implements EventoRepository {

    private final SpringDataEventoRepository repository;

    @Override
    public List<Evento> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Evento save(Evento evento) {
        return repository.save(evento);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
