package chronos.tech.service;

import chronos.tech.model.classes.Secretaria;
import chronos.tech.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SecretariaService {

    @Autowired
    private SecretariaRepository repository;

    //Método para pegar todos os secretaria
    public List<Secretaria> getAllSecretaria(){
        return repository.findAll();
    }

    //Método pegar um secretaria específico
    public Optional<Secretaria> getSecretaria(Long id){
        return repository.findById(id);
    }

    //Salvar no banco de dados h2
    public Secretaria saveSecretaria(Secretaria secretaria){
        return repository.save(secretaria);
    }

    //Atualizar o secretaria
    public Secretaria updateSecretaria(Long id, Secretaria secretariaAtualizado){
        Secretaria secretariaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretaria não encontrado com o ID: " + id));

        if (secretariaAtualizado.getNome_secretaria() != null) secretariaExistente.setNome_secretaria(secretariaAtualizado.getNome_secretaria());
        if (secretariaAtualizado.getDescricao_secretaria() != null) secretariaExistente.setDescricao_secretaria(secretariaAtualizado.getDescricao_secretaria());

        return repository.save(secretariaExistente);
    }

    //Deletar um secretaria
    public void deletSecretaria(Long id){
        repository.deleteById(id);
    }


}
