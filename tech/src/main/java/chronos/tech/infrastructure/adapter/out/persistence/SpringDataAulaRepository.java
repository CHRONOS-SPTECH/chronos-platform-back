package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SpringDataAulaRepository extends JpaRepository<Aula, Integer> {
    // O Spring Data vai entender o caminho: atributo "instrutor" -> "idPessoa" + "dataAula"
    List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula);

    // O Spring Data vai entender o caminho: atributo "turma" -> "idTurma" + "dataAula"
    List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula);
}
