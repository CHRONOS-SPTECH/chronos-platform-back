package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.StatusAula;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name = "aula")
@Table(name = "aula")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Integer idAula;
    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;
    @ManyToOne
    @JoinColumn(name = "id_tema")
    private TemaAula tema;
    @ManyToOne
    @JoinColumn(name = "id_instrutor")
    private Pessoa instrutor;
    @Column(name = "data_aula")
    private Date dataAula;
    @Column(name = "hora_inicio")
    private Time horaInicio;
    @Column(name = "hora_fim")
    private Time horaFim;
    @Enumerated(EnumType.STRING)
    private StatusAula statusAula;
    @Column(name = "data_criacao_registro")
    private LocalDateTime data_criacao_registro;

    @OneToMany(mappedBy = "aula")
    private List<ChamadaAula> chamadaAulas;




}
