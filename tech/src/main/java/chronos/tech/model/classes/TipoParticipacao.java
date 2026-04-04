package chronos.tech.model.classes;

import chronos.tech.model.enums.TipoHoraGerada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tipo_participacao")
@Table(name = "tipo_participacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipoParticipacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_participacao;

    private String descricao;
    private TipoHoraGerada tipo_hora_gerada;

}
