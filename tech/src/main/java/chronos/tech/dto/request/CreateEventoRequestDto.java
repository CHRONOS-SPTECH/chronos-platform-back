package chronos.tech.dto.request;

import chronos.tech.model.classes.CategoriaAtividade;
import chronos.tech.model.classes.Secretaria;
import chronos.tech.model.classes.TemaAula;
import chronos.tech.model.classes.Turma;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEventoRequestDto(String titulo, LocalDate data_evento, LocalTime hora_inicio_evento, LocalTime hora_fim_evento, LocalTime hora_inicio_formacao, LocalTime hora_fim_formacao, CategoriaAtividade id_categoria, Secretaria id_secretaria, Turma id_turma, TemaAula id_tema) {
}
