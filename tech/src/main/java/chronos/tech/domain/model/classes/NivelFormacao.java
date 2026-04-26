package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "nivel_formacao")
@Table(name = "nivel_formacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NivelFormacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel")
    private Integer idNivel;
    @Column(name = "nome_nivel")
    private String nomeNivel;
    @Column(name = "ordem_hierarquia")
    private Integer ordemHierarquia;

}
