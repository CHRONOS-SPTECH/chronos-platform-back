package chronos.tech.application.service;

import chronos.tech.application.dto.request.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.response.PerfilAcessoResponseDTO;
import chronos.tech.application.mapper.PerfilAcessoMapper;
import chronos.tech.application.port.in.PerfilAcessoUseCase;
import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.port.PerfilAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilAcessoService implements PerfilAcessoUseCase {

    private final PerfilAcessoRepository repository;
    private final PerfilAcessoMapper mapper;

    //Método para pegar todos os perfilAcesso
    @Override
    public List<PerfilAcessoResponseDTO> getAllPerfilAcesso(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um perfilAcesso específico
    @Override
    public PerfilAcessoResponseDTO getPerfilAcesso(Long id){
        PerfilAcesso perfilAcesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));
        return mapper.toResponse(perfilAcesso);
    }

    //Salvar no banco de dados h2
    @Override
    public PerfilAcessoResponseDTO savePerfilAcesso(PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcesso perfil = mapper.toModel(perfilAcesso);
        return mapper.toResponse(repository.save(perfil));
    }

    //Atualizar o perfilAcesso
    @Override
    public PerfilAcessoResponseDTO updatePerfilAcesso(Long id, PerfilAcessoRequestDTO perfilAcessoAtualizado){
        PerfilAcesso perfilAcessoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));

        mapper.updateFromDto(perfilAcessoAtualizado, perfilAcessoExistente);
        return mapper.toResponse(repository.save(perfilAcessoExistente));
    }

    //Deletar um perfilAcesso
    @Override
    public void deletPerfilAcesso(Long id){
        repository.deleteById(id);
    }

}
