package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Evento;
import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    List<Evento> findAll();

    Optional<Evento> findById(Long id);

    Evento save(Evento evento);

    void deleteById(Long id);

    //@Query("SELECT e FROM Evento e WHERE data_nascimento = :data")
    //List<Evento> buscarPorData(LocalDate data) {
    //}

}
