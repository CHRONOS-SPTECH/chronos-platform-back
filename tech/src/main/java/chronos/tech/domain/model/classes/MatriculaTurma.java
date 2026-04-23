package chronos.tech.domain.model.classes;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_matricula_turma;
    @ManyToOne
    @JoinColumn(name = "fk_turma")
    private Turma id_turma;
    @ManyToOne
    @JoinColumn(name = "fk_pessoa")
    private Pessoa id_pessoa;
    private LocalDate data_matricula;

}
