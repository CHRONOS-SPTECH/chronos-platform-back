package chronos.tech.application.service;

import chronos.tech.application.port.in.CategoriaAtividadeUseCase;
import chronos.tech.application.dto.request.CategoriaAtividadeRequestDTO;
import chronos.tech.application.dto.response.CategoriaAtividadeResponseDTO;
import chronos.tech.application.mapper.CategoriaAtividadeMapper;
import chronos.tech.domain.model.classes.CategoriaAtividade;
import chronos.tech.domain.port.CategoriaAtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaAtividadeService implements CategoriaAtividadeUseCase {

    private final CategoriaAtividadeRepository repository;
    private final CategoriaAtividadeMapper mapper;

    //Método para pegar todos os categoriaAtividade
    @Override
    public List<CategoriaAtividadeResponseDTO> getAllCategoriaAtividade(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um categoriaAtividade específico
    @Override
    public CategoriaAtividadeResponseDTO getCategoriaAtividade(Long id){
        CategoriaAtividade categoriaAtividade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível achar"));
        return mapper.toResponse(categoriaAtividade);
    }

    //Salvar no banco de dados h2
    @Override
    public CategoriaAtividadeResponseDTO saveCategoriaAtividade(CategoriaAtividadeRequestDTO categoriaAtividade){
        CategoriaAtividade entidade = mapper.toModel(categoriaAtividade);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar o categoriaAtividade
    @Override
    public CategoriaAtividadeResponseDTO updateCategoriaAtividade(Long id, CategoriaAtividadeRequestDTO categoriaAtividadeAtualizado){
        CategoriaAtividade categoriaAtividadeExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaAtividade não encontrado com o ID: " + id));

        mapper.updateFromDto(categoriaAtividadeAtualizado, categoriaAtividadeExistente);
        return mapper.toResponse(repository.save(categoriaAtividadeExistente));
    }

    //Deletar um categoriaAtividade
    @Override
    public void deletCategoriaAtividade(Long id){
        repository.deleteById(id);
    }

}
