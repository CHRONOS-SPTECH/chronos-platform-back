package chronos.tech.ControllerTest;

import chronos.tech.application.dto.response.*;
import chronos.tech.application.port.in.AulaUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.security.JwtService;
import chronos.tech.infrastructure.web.AulaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AulaController.class)
@AutoConfigureMockMvc(addFilters = false)
class AulaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AulaUseCase service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private final String aulaJson = """
        {
          "data_aula":"2026-06-01",
          "hora_inicio":"08:00:00",
          "hora_fim":"12:00:00",
          "id_turma":1,
          "id_tema":1,
          "id_instrutor":1
        }
        """;

    @Test
    void deveListarAulas() throws Exception {

        AulaResponseDTO aula = mock(AulaResponseDTO.class);

        when(service.getAllAulas())
                .thenReturn(List.of(aula));

        mockMvc.perform(get("/aulas"))
                .andExpect(status().isOk());

        verify(service).getAllAulas();
    }

    @Test
    void deveBuscarAulaPorId() throws Exception {

        Integer id = 1;

        AulaResponseDTO aula = mock(AulaResponseDTO.class);

        when(service.getAula(id))
                .thenReturn(aula);

        mockMvc.perform(get("/aulas/{id}", id))
                .andExpect(status().isOk());

        verify(service).getAula(id);
    }

    @Test
    void deveCriarAula() throws Exception {

        AulaResponseDTO response =
                mock(AulaResponseDTO.class);

        when(service.saveAula(any()))
                .thenReturn(response);

        mockMvc.perform(post("/aulas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aulaJson))
                .andExpect(status().isCreated());

        verify(service).saveAula(any());
    }

    @Test
    void deveAtualizarAula() throws Exception {

        Integer id = 1;

        AulaResponseDTO response =
                mock(AulaResponseDTO.class);

        when(service.updateAula(eq(id), any()))
                .thenReturn(response);

        mockMvc.perform(put("/aulas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aulaJson))
                .andExpect(status().isOk());

        verify(service).updateAula(eq(id), any());
    }

    @Test
    void deveDeletarAula() throws Exception {

        Integer id = 1;

        doNothing().when(service)
                .deleteAula(id);

        mockMvc.perform(delete("/aulas/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).deleteAula(id);
    }

    @Test
    void deveBuscarAulasDoDia() throws Exception {

        AulaComTemaEMateriaResponseDTO dto =
                mock(AulaComTemaEMateriaResponseDTO.class);

        when(service.getAulasDoDia(any(Date.class), eq(1)))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/aulas/dia")
                        .param("data", "2026-06-01")
                        .param("instrutorId", "1"))
                .andExpect(status().isOk());

        verify(service)
                .getAulasDoDia(any(Date.class), eq(1));
    }

    @Test
    void deveBuscarTodasAulasDetalhadas() throws Exception {

        AulaComTemaEMateriaComInstrutorResponseDTO dto =
                mock(AulaComTemaEMateriaComInstrutorResponseDTO.class);

        when(service.getAllAulasDetails())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/aulas/detalhadas"))
                .andExpect(status().isOk());

        verify(service).getAllAulasDetails();
    }

    @Test
    void deveBuscarAulaDetalhadaPorId() throws Exception {

        Integer id = 1;

        AulaComTemaEMateriaResponseDTO dto =
                mock(AulaComTemaEMateriaResponseDTO.class);

        when(service.getAulaComTemaEMateriaPorId(id))
                .thenReturn(dto);

        mockMvc.perform(get("/aulas/{id}/detalhada", id))
                .andExpect(status().isOk());

        verify(service)
                .getAulaComTemaEMateriaPorId(id);
    }

    @Test
    void deveImportarPlanilha() throws Exception {

        RelatorioImportacaoResponseDTO relatorio =
                mock(RelatorioImportacaoResponseDTO.class);

        when(service.importarCronograma(any()))
                .thenReturn(relatorio);

        MockMultipartFile arquivo =
                new MockMultipartFile(
                        "file",
                        "cronograma.xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "teste".getBytes()
                );

        mockMvc.perform(multipart("/aulas/importar")
                        .file(arquivo))
                .andExpect(status().isOk());

        verify(service).importarCronograma(any());
    }

    @Test
    void deveBuscarAulasPorTurma() throws Exception {

        Integer idTurma = 1;

        AulaComTemaEMateriaComInstrutorResponseDTO dto =
                mock(AulaComTemaEMateriaComInstrutorResponseDTO.class);

        when(service.getAulasPorTurma(idTurma))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/aulas/turma/{id}", idTurma))
                .andExpect(status().isOk());

        verify(service).getAulasPorTurma(idTurma);
    }

    @Test
    void deveRemanejarAulasEmLote() throws Exception {

        String json = """
            [
              {
                "idAula": 1,
                "novaData": "2026-06-10"
              }
            ]
            """;

        doNothing().when(service)
                .remanejarAulasEmLote(any());

        mockMvc.perform(patch("/aulas/remanejar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        verify(service)
                .remanejarAulasEmLote(any());
    }
}