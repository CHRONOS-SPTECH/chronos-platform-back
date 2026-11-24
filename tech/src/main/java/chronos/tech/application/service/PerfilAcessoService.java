package chronos.tech.application.service;

import chronos.tech.application.dto.cadastro.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.cadastro.PerfilAcessoResponseDTO;
import chronos.tech.application.mapper.PerfilAcessoMapper;
import chronos.tech.application.port.in.PerfilAcessoUseCase;
import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.model.classes.Tela;
import chronos.tech.domain.port.PerfilAcessoRepository;
import chronos.tech.domain.port.TelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilAcessoService implements PerfilAcessoUseCase {

    private final PerfilAcessoRepository repository;
    private final PerfilAcessoMapper mapper;
    private final TelaRepository telaRepository;

    //Método para pegar todos os perfilAcesso
    @Override
    public List<PerfilAcessoResponseDTO> getAllPerfilAcesso(){
        return repository.findAll().stream().map(mapper::withTelas).toList();
    }

    //Método pegar um perfilAcesso específico
    @Override
    public PerfilAcessoResponseDTO getPerfilAcesso(Long id){
        PerfilAcesso perfilAcesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));
        return mapper.withTelas(perfilAcesso);
    }

    //Salvar no banco de dados h2
    @Override
    public PerfilAcessoResponseDTO savePerfilAcesso(PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcesso perfil = mapper.toModel(perfilAcesso);
        perfil.setStatusAtivo(perfilAcesso.status_ativo() == null ? Boolean.TRUE : perfilAcesso.status_ativo());
        perfil.setTelas(resolveTelas(perfilAcesso.telas_ids()));
        return mapper.withTelas(repository.save(perfil));
    }

    //Atualizar o perfilAcesso
    @Override
    public PerfilAcessoResponseDTO updatePerfilAcesso(Long id, PerfilAcessoRequestDTO perfilAcessoAtualizado){
        PerfilAcesso perfilAcessoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));

        mapper.updateFromDto(perfilAcessoAtualizado, perfilAcessoExistente);
        perfilAcessoExistente.setTelas(resolveTelas(perfilAcessoAtualizado.telas_ids()));
        if (perfilAcessoAtualizado.status_ativo() != null) {
            perfilAcessoExistente.setStatusAtivo(perfilAcessoAtualizado.status_ativo());
        }
        return mapper.withTelas(repository.save(perfilAcessoExistente));
    }

    //Deletar um perfilAcesso
    @Override
    public void deletPerfilAcesso(Long id){
        PerfilAcesso perfil = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PerfilAcesso não encontrado com o ID: " + id));
        perfil.setStatusAtivo(Boolean.FALSE);
        repository.save(perfil);
    }

    private List<Tela> resolveTelas(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        List<Tela> telas = new ArrayList<>();
        for (Integer id : ids) {
            telas.add(telaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tela não encontrada com o ID: " + id)));
        }
        return telas;
    }
}
