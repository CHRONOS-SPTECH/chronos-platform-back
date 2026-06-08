package chronos.tech.ControllerTest;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.port.in.PessoaUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.security.JwtService;
import chronos.tech.infrastructure.web.PessoaController;
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

@WebMvcTest(PessoaController.class)
@AutoConfigureMockMvc(addFilters = false)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PessoaUseCase service;

    // Beans da segurança
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveRetornarListaDePessoas() throws Exception {

        PessoaResponseDTO pessoa = mock(PessoaResponseDTO.class);

        when(service.getAllPersons())
                .thenReturn(List.of(pessoa));

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk());

        verify(service).getAllPersons();
    }

    @Test
    void deveRetornarPessoaPorId() throws Exception {

        Long id = 1L;

        PessoaResponseDTO pessoa = mock(PessoaResponseDTO.class);

        when(service.pegarPorId(id))
                .thenReturn(pessoa);

        mockMvc.perform(get("/pessoas/{id}", id))
                .andExpect(status().isOk());

        verify(service).pegarPorId(id);
    }

    @Test
    void deveRetornarListaDePessoasDetalhadas() throws Exception {

        PessoaDetalhadaResponseDTO pessoa =
                mock(PessoaDetalhadaResponseDTO.class);

        when(service.getAllPersonsDetails())
                .thenReturn(List.of(pessoa));

        mockMvc.perform(get("/pessoas/details"))
                .andExpect(status().isOk());

        verify(service).getAllPersonsDetails();
    }

    @Test
    void deveRetornarPessoaDetalhadaPorId() throws Exception {

        Long id = 1L;

        PessoaDetalhadaResponseDTO pessoa =
                mock(PessoaDetalhadaResponseDTO.class);

        when(service.getPersonsDetailsId(id))
                .thenReturn(pessoa);

        mockMvc.perform(get("/pessoas/details/{id}", id))
                .andExpect(status().isOk());

        verify(service).getPersonsDetailsId(id);
    }

    @Test
    void deveCriarPessoa() throws Exception {

        PessoaResponseDTO response =
                mock(PessoaResponseDTO.class);

        when(service.createPessoa(any(PessoaRequestDTO.class)))
                .thenReturn(response);

        String json = """
        {
            "nome":"João Silva",
            "email":"joao@email.com",
            "telefone":"11999999999",
            "tipo_vinculo_id":1
        }
        """;

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).createPessoa(any(PessoaRequestDTO.class));
    }

    @Test
    void deveAtualizarPessoa() throws Exception {

        Long id = 1L;

        PessoaRequestDTO request =
                mock(PessoaRequestDTO.class);

        PessoaResponseDTO response =
                mock(PessoaResponseDTO.class);

        when(service.updatePessoa(
                eq(id),
                any(PessoaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/pessoas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(service)
                .updatePessoa(eq(id), any(PessoaRequestDTO.class));
    }

    @Test
    void deveDeletarPessoa() throws Exception {

        Long id = 1L;

        doNothing().when(service).deletePessoa(id);

        mockMvc.perform(delete("/pessoas/{id}", id))
                .andExpect(status().isOk());

        verify(service).deletePessoa(id);
    }
}