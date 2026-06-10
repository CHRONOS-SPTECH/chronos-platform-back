package chronos.tech.ControllerTest;

import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.port.in.ChamadaAulaUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.security.JwtService;
import chronos.tech.infrastructure.web.ChamadaAulaController;
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

@WebMvcTest(ChamadaAulaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChamadaAulaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChamadaAulaUseCase service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private final String chamadaJson = """
        {
          "id_aula": 1,
          "id_pessoa": 1,
          "compareceu": true
        }
        """;

    @Test
    void deveListarChamadas() throws Exception {

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.getAllChamadas())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/chamadas-aula"))
                .andExpect(status().isOk());

        verify(service).getAllChamadas();
    }

    @Test
    void deveBuscarChamadaPorId() throws Exception {

        Long id = 1L;

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.getChamada(id))
                .thenReturn(response);

        mockMvc.perform(get("/chamadas-aula/{id}", id))
                .andExpect(status().isOk());

        verify(service).getChamada(id);
    }

    @Test
    void deveCriarChamada() throws Exception {

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.saveChamada(any()))
                .thenReturn(response);

        mockMvc.perform(post("/chamadas-aula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chamadaJson))
                .andExpect(status().isCreated());

        verify(service).saveChamada(any());
    }

    @Test
    void deveAtualizarChamada() throws Exception {

        Long id = 1L;

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.updateChamada(
                eq(id),
                any()))
                .thenReturn(response);

        mockMvc.perform(put("/chamadas-aula/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chamadaJson))
                .andExpect(status().isOk());

        verify(service)
                .updateChamada(eq(id), any());
    }

    @Test
    void deveDeletarChamada() throws Exception {

        Long id = 1L;

        doNothing().when(service)
                .deleteChamada(id);

        mockMvc.perform(delete("/chamadas-aula/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).deleteChamada(id);
    }

    @Test
    void deveCriarChamadasEmLote() throws Exception {

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.saveListaChamada(any()))
                .thenReturn(List.of(response));

        String json = """
            {
              "id_aula": 1,
              "alunos": [
                {
                  "id_pessoa": 1,
                  "compareceu": true
                },
                {
                  "id_pessoa": 2,
                  "compareceu": false
                }
              ]
            }
            """;

        mockMvc.perform(post("/chamadas-aula/em-lote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).saveListaChamada(any());
    }

    @Test
    void deveBuscarChamadasPorAula() throws Exception {

        Long idAula = 1L;

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(service.getChamadasByAula(idAula))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/chamadas-aula/aula/{id_aula}", idAula))
                .andExpect(status().isOk());

        verify(service).getChamadasByAula(idAula);
    }
}