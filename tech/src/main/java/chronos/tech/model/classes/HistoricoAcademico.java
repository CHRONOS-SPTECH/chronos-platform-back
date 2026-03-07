package chronos.tech.model.classes;

import chronos.tech.model.enums.StatusMateria;

import java.time.LocalDate;

public class HistoricoAcademico {

    private Long id_historico;
    private Pessoa id_pessoa;
    private Materia id_materia;
    private StatusMateria status_materia;
    private LocalDate data_status;

}
