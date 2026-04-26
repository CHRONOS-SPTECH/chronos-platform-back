package chronos.tech.domain.model.classes.compostas;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParticipacaoEventoId implements Serializable {
    private Integer idEvento;
    private Integer idPessoa;
    private Integer idTipoParticipacao;
}
