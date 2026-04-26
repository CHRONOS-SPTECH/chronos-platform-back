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
    @Column(name = "id_historico")
    private Integer idHistorico;
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa idPessoa;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia idMateria;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_materia")
    private StatusMateria statusMateria;
    @Column(name = "data_status")
    private LocalDate dataStatus;

}
