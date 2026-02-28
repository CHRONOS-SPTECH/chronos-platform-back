package chronos.tech.domain.model.classes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

        private Long id_evento;
        private String titulo;
        private LocalDate data_evento;
        private LocalTime hora_inicio_evento;
        private LocalTime hora_fim_evento;
        private LocalTime hora_inicio_formacao;
        private LocalTime hora_fim_formacao;
        private CategoriaAtividade id_categoria;
        private Secretaria id_secretaria;
        private Turma id_turma;
        private TemaAula id_tema;

}
