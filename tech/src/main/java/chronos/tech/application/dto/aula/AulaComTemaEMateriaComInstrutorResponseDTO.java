package chronos.tech.application.dto.aula;

import chronos.tech.application.dto.cadastro.MateriaResponseDTO;
import chronos.tech.application.dto.cadastro.TemaAulaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaResumidoResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;
import lombok.Getter;

@Getter
public class AulaComTemaEMateriaComInstrutorResponseDTO {
    private AulaResponseDTO aula;
    private TemaAulaResponseDTO tema;
    private MateriaResponseDTO materia;
    private PessoaResumidoResponseDTO instrutor;
    private TurmaResponseDTO turma;
    private Boolean chamadaFeita;

    public AulaComTemaEMateriaComInstrutorResponseDTO(AulaResponseDTO aula, TemaAulaResponseDTO tema, MateriaResponseDTO materia, PessoaResumidoResponseDTO instrutor, TurmaResponseDTO turma, Boolean chamadaFeita) {
        this.aula = aula;
        this.tema = tema;
        this.materia = materia;
        this.instrutor = instrutor;
        this.turma = turma;
        this.chamadaFeita = chamadaFeita;
    }
}