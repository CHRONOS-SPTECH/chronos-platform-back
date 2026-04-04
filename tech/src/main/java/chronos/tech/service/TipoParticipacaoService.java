package chronos.tech.service;

import chronos.tech.model.classes.TipoParticipacao;
import chronos.tech.repository.TipoParticipacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TipoParticipacaoService {

    @Autowired
    private TipoParticipacaoRepository repository;

    //Método para pegar todos os tipoParticipacao
    public List<TipoParticipacao> getAllTipoParticipacao(){
        return repository.findAll();
    }

    //Método pegar um tipoParticipacao específico
    public Optional<TipoParticipacao> getTipoParticipacao(Long id){
        return repository.findById(id);
    }

    //Salvar no banco de dados h2
    public TipoParticipacao saveTipoParticipacao(TipoParticipacao tipoParticipacao){
        return repository.save(tipoParticipacao);
    }

    //Atualizar o tipoParticipacao
    public TipoParticipacao updateTipoParticipacao(Long id, TipoParticipacao tipoParticipacaoAtualizado){
        TipoParticipacao tipoParticipacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoParticipacao não encontrado com o ID: " + id));

        if (tipoParticipacaoAtualizado.getDescricao() != null) tipoParticipacaoExistente.setDescricao(tipoParticipacaoAtualizado.getDescricao());
        if (tipoParticipacaoAtualizado.getTipo_hora_gerada() != null) tipoParticipacaoExistente.setTipo_hora_gerada(tipoParticipacaoAtualizado.getTipo_hora_gerada());

        return repository.save(tipoParticipacaoExistente);
    }

    //Deletar um tipoParticipacao
    public void deletTipoParticipacao(Long id){
        repository.deleteById(id);
    }

}
