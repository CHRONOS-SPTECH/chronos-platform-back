package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tipo_vinculo")
@Table(name = "tipo_vinculo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipoVinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_vinculo")
    private Integer idTipoVinculo;
    @Column(name = "nome_vinculo")
    private String nome_vinculo;
    private String descricao;

}
