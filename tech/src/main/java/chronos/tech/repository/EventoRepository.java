package chronos.tech.repository;

import chronos.tech.model.classes.Evento;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    //@Query("SELECT e FROM Evento e WHERE data_nascimento = :data")
    //List<Evento> buscarPorData(LocalDate data) {
    //}

}
