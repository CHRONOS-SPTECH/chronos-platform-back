package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "participacao")
@Table(name = "participacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_participacao;
    @ManyToOne
    @JoinColumn(name = "fk_evento")
    private Evento id_evento;
    @ManyToOne
    @JoinColumn(name = "fk_pessoa")
    private Pessoa id_pessoa;
    @ManyToOne
    @JoinColumn(name = "fk_tipo_participacao")
    private TipoParticipacao id_tipo_participacao;
    private Boolean compareceu;
}
