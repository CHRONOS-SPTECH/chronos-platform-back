package chronos.tech.application.dto.aula;

import chronos.tech.application.dto.cadastro.MateriaResponseDTO;
import chronos.tech.application.dto.cadastro.TemaAulaResponseDTO;
import lombok.Getter;

@Getter
public class AulaComTemaEMateriaResponseDTO {
    private AulaResponseDTO aula;
    private TemaAulaResponseDTO tema;
    private MateriaResponseDTO materia;
    private Boolean chamadaFeita;

    public AulaComTemaEMateriaResponseDTO(AulaResponseDTO aula, TemaAulaResponseDTO tema, MateriaResponseDTO materia, Boolean chamadaFeita) {
        this.aula = aula;
        this.tema = tema;
        this.materia = materia;
        this.chamadaFeita = chamadaFeita;
    }
}
