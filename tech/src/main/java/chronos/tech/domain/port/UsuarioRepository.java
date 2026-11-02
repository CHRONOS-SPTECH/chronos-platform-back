package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmailLogin(String emailLogin);

    Optional<Usuario> findByPessoaIdPessoa(Integer pessoaId);

    Usuario save(Usuario usuario);

    void deleteById(Long id);
}
