package chronos.tech.ControllerTest;

import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.application.port.in.UsuarioUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.security.JwtService;
import chronos.tech.infrastructure.web.UsuarioController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioUseCase service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveListarUsuarios() throws Exception {

        UsuarioResponseDTO usuario =
                mock(UsuarioResponseDTO.class);

        when(service.pegarTodosUsuarios())
                .thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());

        verify(service).pegarTodosUsuarios();
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {

        Long id = 1L;

        UsuarioResponseDTO usuario =
                mock(UsuarioResponseDTO.class);

        when(service.pegarUsuarioPorId(id))
                .thenReturn(usuario);

        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isOk());

        verify(service).pegarUsuarioPorId(id);
    }

    @Test
    void deveCadastrarUsuario() throws Exception {

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(service.criarUsuario(any()))
                .thenReturn(response);

        String json = """
            {
              "pessoa": {
                "nome": "João Silva",
                "email": "joao@email.com",
                "telefone": "11999999999",
                "tipo_vinculo_id": 1
              },
              "email_login": "admin@chronos.com",
              "senha_hash": "123456",
              "status_ativo": true
            }
            """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).criarUsuario(any());
    }

    @Test
    void deveAtualizarUsuario() throws Exception {

        Long id = 1L;

        UsuarioResponseDTO response =
                mock(UsuarioResponseDTO.class);

        when(service.atualizarUsuario(
                eq(id),
                any()))
                .thenReturn(response);

        String json = """
            {
              "pessoa": {
                "nome": "João Silva",
                "email": "joao@email.com",
                "telefone": "11999999999",
                "tipo_vinculo_id": 1
              },
              "email_login": "admin@chronos.com",
              "senha_hash": "123456",
              "status_ativo": true
            }
            """;

        mockMvc.perform(put("/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service)
                .atualizarUsuario(eq(id), any());
    }

    @Test
    void deveDeletarUsuario() throws Exception {

        Long id = 1L;

        doNothing().when(service)
                .deletarUsuario(id);

        mockMvc.perform(delete("/usuarios/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).deletarUsuario(id);
    }
}