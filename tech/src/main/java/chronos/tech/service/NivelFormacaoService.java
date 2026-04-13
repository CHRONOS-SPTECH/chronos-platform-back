package chronos.tech.service;

import chronos.tech.model.classes.NivelFormacao;
import chronos.tech.repository.NivelFormacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NivelFormacaoService {

    @Autowired
    private NivelFormacaoRepository repository;

    //Método para pegar todos os nivelFormacao
    public List<NivelFormacao> getAllNivelFormacao(){
        return repository.findAll();
    }

    //Método pegar um nivelFormacao específico
    public Optional<NivelFormacao> getNivelFormacao(Long id){
        return repository.findById(id);
    }

    //Salvar no banco de dados h2
    public NivelFormacao saveNivelFormacao(NivelFormacao nivelFormacao){
        return repository.save(nivelFormacao);
    }

    //Atualizar o nivelFormacao
    public NivelFormacao updateNivelFormacao(Long id, NivelFormacao nivelFormacaoAtualizado){
        NivelFormacao nivelFormacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NivelFormacao não encontrado com o ID: " + id));

        if (nivelFormacaoAtualizado.getNome_nivel() != null) nivelFormacaoExistente.setNome_nivel(nivelFormacaoAtualizado.getNome_nivel());
        if (nivelFormacaoAtualizado.getOrdem_hierarquia() != null) nivelFormacaoExistente.setOrdem_hierarquia(nivelFormacaoAtualizado.getOrdem_hierarquia());

        return repository.save(nivelFormacaoExistente);
    }

    //Deletar um nivelFormacao
    public void deletNivelFormacao(Long id){
        repository.deleteById(id);
    }

}
