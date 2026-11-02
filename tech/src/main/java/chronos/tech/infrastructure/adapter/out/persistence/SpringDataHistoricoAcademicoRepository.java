package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.HistoricoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataHistoricoAcademicoRepository extends JpaRepository<HistoricoAcademico, Long> {

        @Query("""
                SELECT h FROM historico_academico h
                JOIN FETCH h.idMateria m
                LEFT JOIN FETCH m.idNivel
                WHERE h.idPessoa.idPessoa = :pessoaId
                ORDER BY h.dataStatus DESC, h.idHistorico DESC
                """)
        List<HistoricoAcademico> findByPessoaId(@Param("pessoaId") Integer pessoaId);

        @Query("SELECT h.idMateria.idNivel.ordemHierarquia, COUNT(DISTINCT h.idPessoa) " +
                "FROM historico_academico h " +
                "WHERE h.idMateria.idNivel IS NOT NULL " +
                "GROUP BY h.idMateria.idNivel.ordemHierarquia " +
                "ORDER BY h.idMateria.idNivel.ordemHierarquia")
        List<Object[]> countAlunosPorNivel();

}
