package chronos.tech.application.service;

import chronos.tech.application.dto.request.SecretariaRequestDTO;
import chronos.tech.application.dto.response.SecretariaResponseDTO;
import chronos.tech.application.mapper.SecretariaMapper;
import chronos.tech.application.port.in.SecretariaUseCase;
import chronos.tech.domain.model.classes.Secretaria;
import chronos.tech.domain.port.SecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecretariaService implements SecretariaUseCase {

    private final SecretariaRepository repository;
    private final SecretariaMapper mapper;

    //Método para pegar todos os secretaria
    @Override
    public List<SecretariaResponseDTO> getAllSecretaria(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um secretaria específico
    @Override
    public SecretariaResponseDTO getSecretaria(Long id){
        Secretaria secretaria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretaria não encontrado com o ID: " + id));
        return mapper.toResponse(secretaria);
    }

    //Salvar no banco de dados h2
    @Override
    public SecretariaResponseDTO saveSecretaria(SecretariaRequestDTO secretaria){
        Secretaria entidade = mapper.toModel(secretaria);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar o secretaria
    @Override
    public SecretariaResponseDTO updateSecretaria(Long id, SecretariaRequestDTO secretariaAtualizado){
        Secretaria secretariaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretaria não encontrado com o ID: " + id));

        mapper.updateFromDto(secretariaAtualizado, secretariaExistente);
        return mapper.toResponse(repository.save(secretariaExistente));
    }

    //Deletar um secretaria
    @Override
    public void deletSecretaria(Long id){
        repository.deleteById(id);
    }


}
