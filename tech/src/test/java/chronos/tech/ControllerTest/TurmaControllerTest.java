package chronos.tech.ControllerTest;

import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.port.in.TurmaUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.security.JwtService;
import chronos.tech.infrastructure.web.TurmaController;
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

@WebMvcTest(TurmaController.class)
@AutoConfigureMockMvc(addFilters = false)
class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TurmaUseCase service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveListarTurmas() throws Exception {

        TurmaResponseDTO turma =
                mock(TurmaResponseDTO.class);

        when(service.getAllTurmas())
                .thenReturn(List.of(turma));

        mockMvc.perform(get("/turmas"))
                .andExpect(status().isOk());

        verify(service).getAllTurmas();
    }

    @Test
    void deveBuscarTurmaPorId() throws Exception {

        Long id = 1L;

        TurmaResponseDTO turma =
                mock(TurmaResponseDTO.class);

        when(service.getTurma(id))
                .thenReturn(turma);

        mockMvc.perform(get("/turmas/{id}", id))
                .andExpect(status().isOk());

        verify(service).getTurma(id);
    }

    @Test
    void deveCriarTurma() throws Exception {

        TurmaResponseDTO response =
                mock(TurmaResponseDTO.class);

        when(service.saveTurma(any()))
                .thenReturn(response);

        String json = """
            {
              "nome_turma": "Turma Aristóteles 2026",
              "data_inicio": "2026-01-15",
              "data_encerramento": "2026-12-15",
              "status_turma": "Em Andamento"
            }
            """;

        mockMvc.perform(post("/turmas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).saveTurma(any());
    }

    @Test
    void deveAtualizarTurma() throws Exception {

        Long id = 1L;

        TurmaResponseDTO response =
                mock(TurmaResponseDTO.class);

        when(service.updateTurma(
                eq(id),
                any()))
                .thenReturn(response);

        String json = """
            {
              "nome_turma": "Turma Aristóteles 2026",
              "data_inicio": "2026-01-15",
              "data_encerramento": "2026-12-15",
              "status_turma": "Em Andamento"
            }
            """;

        mockMvc.perform(put("/turmas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service)
                .updateTurma(eq(id), any());
    }

    @Test
    void deveDeletarTurma() throws Exception {

        Long id = 1L;

        doNothing().when(service)
                .deleteTurma(id);

        mockMvc.perform(delete("/turmas/{id}", id))
                .andExpect(status().isOk());

        verify(service).deleteTurma(id);
    }

    @Test
    void deveListarAlunosDaTurmaComPresenca() throws Exception {

        Integer idTurma = 1;

        AlunoComPresencaResponseDTO aluno =
                mock(AlunoComPresencaResponseDTO.class);

        when(service.getAlunosDaTurmaComPresenca(idTurma))
                .thenReturn(List.of(aluno));

        mockMvc.perform(get("/turmas/{id}/alunos", idTurma))
                .andExpect(status().isOk());

        verify(service)
                .getAlunosDaTurmaComPresenca(idTurma);
    }
}