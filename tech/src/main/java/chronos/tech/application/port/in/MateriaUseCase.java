package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.MateriaRequestDTO;
import chronos.tech.application.dto.response.MateriaResponseDTO;

import java.util.List;

public interface MateriaUseCase {
    List<MateriaResponseDTO> getAllMaterias();
    MateriaResponseDTO getMateria(Long id);
    MateriaResponseDTO saveMateria(MateriaRequestDTO materia);
    MateriaResponseDTO updateMateria(Long id, MateriaRequestDTO materia);
    void deleteMateria(Long id);
}
