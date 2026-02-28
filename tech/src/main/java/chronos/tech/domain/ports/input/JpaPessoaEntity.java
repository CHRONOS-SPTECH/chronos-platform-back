package chronos.tech.domain.ports.input;

import chronos.tech.domain.model.enums.TipoVinculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class JpaPessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pessoa;
    private String nome;
    private String genero;
    private TipoVinculo tipoVinculo;
    private LocalDate data_nascimento;
    private LocalDate data_ingresso;
    private LocalDate data_membro;
    private LocalDate data_saida;



}
