package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.ParticipacaoEventoId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "participacao_evento")
@Table(name = "participacao_evento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParticipacaoEvento {

    @EmbeddedId
    private ParticipacaoEventoId id;

    @ManyToOne
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento")
    private Evento evento;
    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
    @ManyToOne
    @MapsId("idTipoParticipacao")
    @JoinColumn(name = "id_tipo_participacao")
    private TipoParticipacao idTipoParticipacao;
    private Boolean compareceu;
}
