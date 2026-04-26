package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.ChamadaAulaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chamada_aula")
@Table(name = "chamada_aula")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChamadaAula {

    @EmbeddedId
    private ChamadaAulaId id;

    @ManyToOne
    @MapsId("idAula")
    @JoinColumn(name = "id_aula")
    private Aula aula;
    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
    private Boolean compareceu;

}
