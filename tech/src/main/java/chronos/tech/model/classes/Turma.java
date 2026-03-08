package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "turma")
@Table(name = "turma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_nivel;
    private String nome_turma;
    private LocalDate data_inicio;
    private LocalDate data_encerramento;
    private Boolean status_encerrado;

}
