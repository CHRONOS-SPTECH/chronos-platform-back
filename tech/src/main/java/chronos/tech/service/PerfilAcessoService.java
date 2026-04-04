package chronos.tech.service;

import chronos.tech.model.classes.PerfilAcesso;
import chronos.tech.repository.PerfilAcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PerfilAcessoService {

    @Autowired
    private PerfilAcessoRepository repository;

    //Método para pegar todos os perfilAcesso
    public List<PerfilAcesso> getAllPerfilAcesso(){
        return repository.findAll();
    }

    //Método pegar um perfilAcesso específico
    public Optional<PerfilAcesso> getPerfilAcesso(Long id){
        return repository.findById(id);
    }

    //Salvar no banco de dados h2
    public PerfilAcesso savePerfilAcesso(PerfilAcesso perfilAcesso){
        return repository.save(perfilAcesso);
    }

    //Atualizar o perfilAcesso
    public PerfilAcesso updatePerfilAcesso(Long id, PerfilAcesso perfilAcessoAtualizado){
        PerfilAcesso perfilAcessoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));

        if (perfilAcessoAtualizado.getNome_perfil() != null) perfilAcessoExistente.setNome_perfil(perfilAcessoAtualizado.getNome_perfil());
        if (perfilAcessoAtualizado.getDescricao() != null) perfilAcessoExistente.setDescricao(perfilAcessoAtualizado.getDescricao());

        return repository.save(perfilAcessoExistente);
    }

    //Deletar um perfilAcesso
    public void deletPerfilAcesso(Long id){
        repository.deleteById(id);
    }

}
