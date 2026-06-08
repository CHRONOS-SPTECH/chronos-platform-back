package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.application.mapper.UsuarioMapper;
import chronos.tech.application.service.UsuarioService;
import chronos.tech.domain.model.classes.Usuario;
import chronos.tech.domain.port.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioService service;

    @Test
    void deveCriarUsuarioComSucesso() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        Usuario usuario = new Usuario();
        Usuario usuarioSalvo = new Usuario();

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(mapper.toModel(dto))
                .thenReturn(usuario);

        when(repository.save(usuario))
                .thenReturn(usuarioSalvo);

        when(mapper.toResponse(usuarioSalvo))
                .thenReturn(response);

        UsuarioResponseDTO resultado =
                service.criarUsuario(dto);

        assertNotNull(resultado);

        verify(mapper).toModel(dto);
        verify(repository).save(usuario);
        verify(mapper).toResponse(usuarioSalvo);
    }

    @Test
    void deveBuscarUsuarioPorId() {

        Usuario usuario = new Usuario();

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(mapper.toResponse(usuario))
                .thenReturn(response);

        UsuarioResponseDTO resultado =
                service.pegarUsuarioPorId(1L);

        assertNotNull(resultado);

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.pegarUsuarioPorId(1L)
                );

        assertEquals(
                "Não foi possível",
                exception.getMessage()
        );
    }

    @Test
    void deveListarTodosUsuarios() {

        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(repository.findAll())
                .thenReturn(List.of(usuario1, usuario2));

        when(mapper.toResponse(any(Usuario.class)))
                .thenReturn(response);

        List<UsuarioResponseDTO> resultado =
                service.pegarTodosUsuarios();

        assertEquals(2, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremUsuarios() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<UsuarioResponseDTO> resultado =
                service.pegarTodosUsuarios();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        Usuario usuarioExistente =
                new Usuario();

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuarioExistente));

        when(repository.save(usuarioExistente))
                .thenReturn(usuarioExistente);

        when(mapper.toResponse(usuarioExistente))
                .thenReturn(response);

        UsuarioResponseDTO resultado =
                service.atualizarUsuario(1L, dto);

        assertNotNull(resultado);

        verify(mapper)
                .updateFromDto(dto, usuarioExistente);

        verify(repository)
                .save(usuarioExistente);
    }

    @Test
    void naoDeveAtualizarUsuarioInexistente() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.atualizarUsuario(1L, dto)
                );

        assertEquals(
                "Usuario não encontrado com o ID: 1",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveSalvarQuandoUsuarioNaoExistirNoUpdate() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.atualizarUsuario(1L, dto)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void deveDeletarUsuario() {

        service.deletarUsuario(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deveExecutarDeleteUmaUnicaVez() {

        service.deletarUsuario(1L);

        verify(repository, times(1))
                .deleteById(1L);
    }

    @Test
    void devePropagarErroAoSalvarUsuario() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        Usuario usuario = new Usuario();

        when(mapper.toModel(dto))
                .thenReturn(usuario);

        when(repository.save(usuario))
                .thenThrow(
                        new RuntimeException("Erro no banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.criarUsuario(dto)
                );

        assertEquals(
                "Erro no banco",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoListarUsuarios() {

        when(repository.findAll())
                .thenThrow(
                        new RuntimeException("Falha banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.pegarTodosUsuarios()
                );

        assertEquals(
                "Falha banco",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoBuscarUsuario() {

        when(repository.findById(1L))
                .thenThrow(
                        new RuntimeException("Banco indisponível")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.pegarUsuarioPorId(1L)
                );

        assertEquals(
                "Banco indisponível",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoDeletarUsuario() {

        doThrow(
                new RuntimeException("Erro ao deletar")
        ).when(repository)
                .deleteById(1L);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.deletarUsuario(1L)
                );

        assertEquals(
                "Erro ao deletar",
                exception.getMessage()
        );
    }

    @Test
    void deveChamarUpdateFromDtoUmaUnicaVez() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        Usuario usuario =
                new Usuario();

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(repository.save(usuario))
                .thenReturn(usuario);

        when(mapper.toResponse(usuario))
                .thenReturn(
                        mock(UsuarioResponseDTO.class)
                );

        service.atualizarUsuario(1L, dto);

        verify(mapper, times(1))
                .updateFromDto(dto, usuario);
    }

    @Test
    void deveChamarSaveUmaUnicaVezAoCriarUsuario() {

        UsuarioRequestDTO dto = criarUsuarioDTO();

        Usuario usuario = new Usuario();

        when(mapper.toModel(dto))
                .thenReturn(usuario);

        when(repository.save(usuario))
                .thenReturn(usuario);

        when(mapper.toResponse(usuario))
                .thenReturn(
                        mock(UsuarioResponseDTO.class)
                );

        service.criarUsuario(dto);

        verify(repository, times(1))
                .save(usuario);
    }

    private UsuarioRequestDTO criarUsuarioDTO() {

        PessoaRequestDTO pessoa =
                new PessoaRequestDTO(
                        "João Silva",
                        "joao@email.com",
                        "11999999999",
                        "Masculino",
                        "12345678901",
                        true,
                        "https://foto.com",
                        1,
                        LocalDate.of(2000, 1, 1),
                        LocalDate.now(),
                        null,
                        null
                );

        return new UsuarioRequestDTO(
                pessoa,
                "admin@email.com",
                "senhaHash123",
                true
        );
    }
}