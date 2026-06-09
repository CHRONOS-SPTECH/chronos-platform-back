package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.sql.Date;
import java.util.List;

public interface SpringDataAulaRepository extends JpaRepository<Aula, Integer> {

    @Query("SELECT DISTINCT a.instrutor FROM aula a WHERE a.instrutor IS NOT NULL")
    List<Pessoa> findInstrutoresAtivos();

}
