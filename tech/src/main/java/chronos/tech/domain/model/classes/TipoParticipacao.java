package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.TipoHoraGerada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "tipo_participacao")
@Table(name = "tipo_participacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipoParticipacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_participacao")
    private Integer idTipoParticipacao;
    private String descricao;
    @Column(name = "tipo_hora_gerada")
    @Enumerated(EnumType.STRING)
    private TipoHoraGerada tipoHoraGerada;

    @OneToMany(mappedBy = "idTipoParticipacao")
    private List<ParticipacaoEvento> participacaoEventos;

}
