package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tema")
@Table(name = "tema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TemaAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tema;
    private String titulo_tema;
    private Integer ordem_prevista;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_materia")
    private Materia id_materia;

}
