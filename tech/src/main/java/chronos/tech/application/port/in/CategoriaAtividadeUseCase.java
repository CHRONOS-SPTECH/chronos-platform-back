package chronos.tech.application.port.in;

import chronos.tech.application.dto.cadastro.CategoriaAtividadeRequestDTO;
import chronos.tech.application.dto.cadastro.CategoriaAtividadeResponseDTO;

import java.util.List;

public interface CategoriaAtividadeUseCase {
    List<CategoriaAtividadeResponseDTO> getAllCategoriaAtividade();

    CategoriaAtividadeResponseDTO getCategoriaAtividade(Long id);

    CategoriaAtividadeResponseDTO saveCategoriaAtividade(CategoriaAtividadeRequestDTO categoriaAtividade);

    CategoriaAtividadeResponseDTO updateCategoriaAtividade(Long id, CategoriaAtividadeRequestDTO categoriaAtividadeAtualizado);

    void deletCategoriaAtividade(Long id);
}
