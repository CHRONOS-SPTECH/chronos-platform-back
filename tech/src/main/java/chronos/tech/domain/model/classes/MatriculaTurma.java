package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "matricula_turma")
@Table(name = "matricula_turma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatriculaTurma {

    @EmbeddedId
    private MatriculaTurmaId id;

    @ManyToOne
    @MapsId("idTurma")
    @JoinColumn(name = "id_turma")
    private Turma turma;
    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
    @Column(name = "data_matricula")
    private LocalDate dataMatricula;

}
