package chronos.tech.application.dto.response;

import lombok.Getter;

@Getter
public class AulaComTemaEMateriaResponseDTO {
    private AulaResponseDTO aula;
    private TemaAulaResponseDTO tema;
    private MateriaResponseDTO materia;

    public AulaComTemaEMateriaResponseDTO(AulaResponseDTO aula, TemaAulaResponseDTO tema, MateriaResponseDTO materia) {
        this.aula = aula;
        this.tema = tema;
        this.materia = materia;
    }
}
