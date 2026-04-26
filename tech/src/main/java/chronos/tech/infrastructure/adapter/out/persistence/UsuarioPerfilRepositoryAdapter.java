package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.UsuarioPerfil;
import chronos.tech.domain.port.UsuarioPerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioPerfilRepositoryAdapter implements UsuarioPerfilRepository {
    private final SpringDataUsuarioPerfilRepository repository;
    public List<UsuarioPerfil> findAll() { return repository.findAll(); }
    public Optional<UsuarioPerfil> findById(Long id) { return repository.findById(id); }
    public List<UsuarioPerfil> findByUsuarioId(Long usuarioId) { return repository.findByUsuarioId(usuarioId); }
    public UsuarioPerfil save(UsuarioPerfil usuarioPerfil) { return repository.save(usuarioPerfil); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
