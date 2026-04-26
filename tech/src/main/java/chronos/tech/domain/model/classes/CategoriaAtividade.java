package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "categoria_atividade")
@Table(name = "categoria_atividade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    private String nome;
    private String descricao;

}
