package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tema_aula")
@Table(name = "tema_aula")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TemaAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tema")
    private Integer idTema;
    @Column(name = "titulo_tema")
    private String tituloTema;
    @Column(name = "ordem_prevista")
    private Integer ordemPrevista;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_materia")
    private Materia idMateria;

}
