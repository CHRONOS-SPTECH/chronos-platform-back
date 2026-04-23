package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.StatusMateria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "historico_academico")
@Table(name = "historico_academico")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoricoAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historico;
    @ManyToOne
    @JoinColumn(name = "fk_pessoa")
    private Pessoa id_pessoa;
    @ManyToOne
    @JoinColumn(name = "fk_materia")
    private Materia id_materia;
    @Enumerated(EnumType.STRING)
    private StatusMateria status_materia;
    private LocalDate data_status;

}
