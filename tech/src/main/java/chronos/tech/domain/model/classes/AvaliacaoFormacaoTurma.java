package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "avaliacao_formacao_turma")
@Table(name = "avaliacao_formacao_turma")
@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoFormacaoTurma {
    @EmbeddedId
    private MatriculaTurmaId id;

    @ManyToOne(optional = false)
    @MapsId("idTurma")
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @ManyToOne(optional = false)
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @Column(name = "status_avaliacao", nullable = false, length = 20)
    private String statusAvaliacao = "PENDENTE";

    @Column(name = "data_decisao")
    private LocalDate dataDecisao;

    @Column(name = "id_usuario_decisor")
    private Integer idUsuarioDecisor;

    @Column(name = "observacao", length = 500)
    private String observacao;
}
