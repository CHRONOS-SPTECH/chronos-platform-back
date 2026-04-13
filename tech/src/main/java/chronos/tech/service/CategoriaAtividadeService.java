package chronos.tech.service;

import chronos.tech.dto.response.CategoriaAtividadeResponseDto;
import chronos.tech.model.classes.CategoriaAtividade;
import chronos.tech.model.classes.Pessoa;
import chronos.tech.repository.CategoriaAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaAtividadeService {

    @Autowired
    private CategoriaAtividadeRepository repository;

    //Método para pegar todos os categoriaAtividade
    public List<CategoriaAtividade> getAllCategoriaAtividade(){
        return repository.findAll();
    }

    //Método pegar um categoriaAtividade específico
    public Optional<CategoriaAtividadeResponseDto> getCategoriaAtividade(Long id){

        CategoriaAtividade categoriaAtividade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível achar"));

        return toResponse(categoriaAtividade);
    }

    private Optional<CategoriaAtividadeResponseDto> toResponse(CategoriaAtividade categoriaAtividade) {
        return Optional.of(new CategoriaAtividadeResponseDto(categoriaAtividade.getDescricao()));
    }

    //Salvar no banco de dados h2
    public CategoriaAtividade saveCategoriaAtividade(CategoriaAtividade categoriaAtividade){
        return repository.save(categoriaAtividade);
    }

    //Atualizar o categoriaAtividade
    public CategoriaAtividade updateCategoriaAtividade(Long id, CategoriaAtividade categoriaAtividadeAtualizado){
        CategoriaAtividade categoriaAtividadeExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaAtividade não encontrado com o ID: " + id));

        if (categoriaAtividadeAtualizado.getDescricao() != null) categoriaAtividadeExistente.setDescricao(categoriaAtividadeAtualizado.getDescricao());

        return repository.save(categoriaAtividadeExistente);
    }

    //Deletar um categoriaAtividade
    public void deletCategoriaAtividade(Long id){
        repository.deleteById(id);
    }

}
