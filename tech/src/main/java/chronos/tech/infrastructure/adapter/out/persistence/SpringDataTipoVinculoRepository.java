package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TipoVinculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface SpringDataTipoVinculoRepository extends JpaRepository<TipoVinculo, Integer> {
    @Query("SELECT v FROM tipo_vinculo v WHERE v.nome_vinculo = :nome")
    Optional<TipoVinculo> findByNome(@Param("nome") String nome);
}
