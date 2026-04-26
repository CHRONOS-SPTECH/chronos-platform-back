package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pessoa")
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer idPessoa;
    @ManyToOne
    @JoinColumn(name = "id_tipo_vinculo")
    private TipoVinculo tipoVinculo;
    private String nome;
    private String email;
    private String telefone;
    private String genero;
    private String cpf;
    private Boolean bolsista;
    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "data_ingresso")
    private LocalDate dataIngresso;
    @Column(name = "data_membro")
    private LocalDate dataMembro;
    @Column(name = "data_saida")
    private LocalDate dataSaida;

    @OneToMany(mappedBy = "pessoa")
    private List<MatriculaTurma> matriculaTurmas;

    @OneToMany(mappedBy = "pessoa")
    private List<ChamadaAula> chamadaAulas;

    @OneToMany(mappedBy = "pessoa")
    private List<GestaoSecretaria> gestaoSecretarias;

    @OneToMany(mappedBy = "pessoa")
    private List<ParticipacaoEvento> participacaoEventos;
}


