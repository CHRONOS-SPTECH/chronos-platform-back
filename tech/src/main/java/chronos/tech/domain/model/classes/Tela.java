package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tela")
@Table(name = "tela")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tela")
    private Integer idTela;

    @Column(name = "codigo_tela", unique = true, nullable = false)
    private String codigoTela;

    @Column(name = "nome_tela", nullable = false)
    private String nomeTela;

    private String grupo;

    private Integer ordem;

    @Column(name = "status_ativo", columnDefinition = "TINYINT")
    private Boolean statusAtivo;
}
