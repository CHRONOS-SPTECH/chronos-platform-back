package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.StatusTurma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity(name = "turma")
@Table(name = "turma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Integer idTurma;

    @Column(name = "nome_turma")
    private String nomeTurma;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @Column(name = "status_turma")
    private StatusTurma statusTurma;

    public Double calcularPercentualConclusao() {
        // 1. Validação de segurança contra valores nulos no banco de dados
        if (this.dataInicio == null || this.dataEncerramento == null) {
            return (this.statusTurma == StatusTurma.CONCLUIDA) ? 100.0 : 0.0;
        }

        LocalDate hoje = LocalDate.now();

        // 2. Validação baseada no status ou se ainda não chegou a data de início
        if (statusTurma == StatusTurma.NAO_INICIADA || hoje.isBefore(dataInicio)) {
            return 0.0;
        }

        // 3. Validação se a data atual já passou do encerramento
        if (hoje.isAfter(dataEncerramento)) {
            return 100.0;
        }

        Long diasTotais = ChronoUnit.DAYS.between(dataInicio, dataEncerramento);

        // Evita divisão por zero caso a data de início e término sejam iguais por erro de digitação
        if (diasTotais <= 0) {
            return 100.0;
        }

        Long diasPassados = ChronoUnit.DAYS.between(dataInicio, hoje);

        return ((double) diasPassados / diasTotais) * 100;
    }

    @OneToMany(mappedBy = "turma")
    private List<MatriculaTurma> matriculaTurmas;

}