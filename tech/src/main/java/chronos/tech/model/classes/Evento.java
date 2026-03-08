package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "evento")
@Table(name = "evento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Evento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_evento;
        private String titulo;
        private LocalDate data_evento;
        private LocalTime hora_inicio_evento;
        private LocalTime hora_fim_evento;
        private LocalTime hora_inicio_formacao;
        private LocalTime hora_fim_formacao;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "fk_categoria")
        private CategoriaAtividade id_categoria;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "fk_secretaria")
        private Secretaria id_secretaria;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "fk_turma")
        private Turma id_turma;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "fk_tema")
        private TemaAula id_tema;

}
