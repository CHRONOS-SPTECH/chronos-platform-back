package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "nivel")
@Table(name = "nivel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NivelFormacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_nivel;
    private String nome_nivel;
    private Integer ordem_hierarquia;

}
