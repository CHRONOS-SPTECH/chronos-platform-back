package chronos.tech.ControllerTest;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.application.port.in.MatriculaTurmaUseCase;
import chronos.tech.infrastructure.security.CustomUserDetailsService;
import chronos.tech.infrastructure.security.JwtAuthenticationFilter;
import chronos.tech.infrastructure.web.MatriculaTurmaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatriculaTurmaController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(MatriculaTurmaControllerTest.MockConfig.class)
class MatriculaTurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MatriculaTurmaUseCase service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @TestConfiguration
    static class MockConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper().findAndRegisterModules();
        }
    }

    @Test
    void deveListarTodasMatriculas() throws Exception {

        MatriculaTurmaResponseDTO response =
                Mockito.mock(MatriculaTurmaResponseDTO.class);

        when(service.getAllMatriculas())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/matriculas-turma"))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarMatriculaPorId() throws Exception {

        MatriculaTurmaResponseDTO response =
                Mockito.mock(MatriculaTurmaResponseDTO.class);

        when(service.getMatricula(1L))
                .thenReturn(response);

        mockMvc.perform(get("/matriculas-turma/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarMatricula() throws Exception {

        MatriculaTurmaRequestDTO request =
                new MatriculaTurmaRequestDTO(
                        1L,
                        1L,
                        LocalDate.of(2025, 1, 1)
                );

        MatriculaTurmaResponseDTO response =
                Mockito.mock(MatriculaTurmaResponseDTO.class);

        when(service.saveMatricula(any(MatriculaTurmaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/matriculas-turma")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarMatricula() throws Exception {

        MatriculaTurmaRequestDTO request =
                new MatriculaTurmaRequestDTO(
                        1L,
                        1L,
                        LocalDate.of(2025, 1, 1)
                );

        MatriculaTurmaResponseDTO response =
                Mockito.mock(MatriculaTurmaResponseDTO.class);

        when(service.updateMatricula(
                eq(1L),
                any(MatriculaTurmaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/matriculas-turma/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarMatricula() throws Exception {

        doNothing().when(service).deleteMatricula(1L);

        mockMvc.perform(delete("/matriculas-turma/1"))
                .andExpect(status().isNoContent());
    }
}