package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.application.mapper.MatriculaTurmaMapper;
import chronos.tech.application.service.MatriculaTurmaService;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.port.MatriculaTurmaRepository;
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
class MatriculaTurmaServiceTest {

    @Mock
    private MatriculaTurmaRepository repository;

    @Mock
    private MatriculaTurmaMapper mapper;

    @InjectMocks
    private MatriculaTurmaService service;

    @Test
    void deveSalvarMatriculaComSucesso() {

        MatriculaTurmaRequestDTO dto = criarDto();

        MatriculaTurma model = new MatriculaTurma();

        MatriculaTurma matriculaSalva = new MatriculaTurma();

        MatriculaTurmaResponseDTO response =
                mock(MatriculaTurmaResponseDTO.class);

        when(mapper.toModel(dto))
                .thenReturn(model);

        when(repository.save(model))
                .thenReturn(matriculaSalva);

        when(mapper.toResponse(matriculaSalva))
                .thenReturn(response);

        MatriculaTurmaResponseDTO resultado =
                service.saveMatricula(dto);

        assertNotNull(resultado);

        verify(mapper).toModel(dto);
        verify(repository).save(model);
        verify(mapper).toResponse(matriculaSalva);
    }

    @Test
    void deveBuscarMatriculaPorId() {

        MatriculaTurma matricula = new MatriculaTurma();

        MatriculaTurmaResponseDTO response =
                mock(MatriculaTurmaResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(matricula));

        when(mapper.toResponse(matricula))
                .thenReturn(response);

        MatriculaTurmaResponseDTO resultado =
                service.getMatricula(1L);

        assertNotNull(resultado);

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoMatriculaNaoExistir() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getMatricula(1L)
                );

        assertEquals(
                "Matricula nao encontrada: 1",
                exception.getMessage()
        );
    }

    @Test
    void deveListarTodasAsMatriculas() {

        MatriculaTurma m1 = new MatriculaTurma();
        MatriculaTurma m2 = new MatriculaTurma();

        MatriculaTurmaResponseDTO response =
                mock(MatriculaTurmaResponseDTO.class);

        when(repository.findAll())
                .thenReturn(List.of(m1, m2));

        when(mapper.toResponse(any(MatriculaTurma.class)))
                .thenReturn(response);

        List<MatriculaTurmaResponseDTO> resultado =
                service.getAllMatriculas();

        assertEquals(2, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremMatriculas() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<MatriculaTurmaResponseDTO> resultado =
                service.getAllMatriculas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarMatriculaComSucesso() {

        MatriculaTurmaRequestDTO dto = criarDto();

        MatriculaTurma existente =
                new MatriculaTurma();

        MatriculaTurmaResponseDTO response =
                mock(MatriculaTurmaResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(existente))
                .thenReturn(existente);

        when(mapper.toResponse(existente))
                .thenReturn(response);

        MatriculaTurmaResponseDTO resultado =
                service.updateMatricula(1L, dto);

        assertNotNull(resultado);

        verify(mapper)
                .updateFromDto(dto, existente);

        verify(repository)
                .save(existente);
    }

    @Test
    void naoDeveAtualizarMatriculaInexistente() {

        MatriculaTurmaRequestDTO dto = criarDto();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.updateMatricula(1L, dto)
                );

        assertEquals(
                "Matricula nao encontrada: 1",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveSalvarQuandoMatriculaNaoExistirNoUpdate() {

        MatriculaTurmaRequestDTO dto = criarDto();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.updateMatricula(1L, dto)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void deveDeletarMatricula() {

        service.deleteMatricula(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deveExecutarDeleteUmaUnicaVez() {

        service.deleteMatricula(1L);

        verify(repository, times(1))
                .deleteById(1L);
    }

    @Test
    void devePropagarErroAoSalvarMatricula() {

        MatriculaTurmaRequestDTO dto = criarDto();

        MatriculaTurma model =
                new MatriculaTurma();

        when(mapper.toModel(dto))
                .thenReturn(model);

        when(repository.save(model))
                .thenThrow(
                        new RuntimeException("Erro no banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.saveMatricula(dto)
                );

        assertEquals(
                "Erro no banco",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoListarMatriculas() {

        when(repository.findAll())
                .thenThrow(
                        new RuntimeException("Falha banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getAllMatriculas()
                );

        assertEquals(
                "Falha banco",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoBuscarMatricula() {

        when(repository.findById(1L))
                .thenThrow(
                        new RuntimeException("Banco indisponivel")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getMatricula(1L)
                );

        assertEquals(
                "Banco indisponivel",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoDeletarMatricula() {

        doThrow(
                new RuntimeException("Erro ao deletar")
        ).when(repository)
                .deleteById(1L);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.deleteMatricula(1L)
                );

        assertEquals(
                "Erro ao deletar",
                exception.getMessage()
        );
    }

    @Test
    void deveChamarUpdateFromDtoUmaUnicaVez() {

        MatriculaTurmaRequestDTO dto = criarDto();

        MatriculaTurma existente =
                new MatriculaTurma();

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(existente))
                .thenReturn(existente);

        when(mapper.toResponse(existente))
                .thenReturn(
                        mock(MatriculaTurmaResponseDTO.class)
                );

        service.updateMatricula(1L, dto);

        verify(mapper, times(1))
                .updateFromDto(dto, existente);
    }

    @Test
    void deveChamarSaveUmaUnicaVezAoCriarMatricula() {

        MatriculaTurmaRequestDTO dto = criarDto();

        MatriculaTurma model =
                new MatriculaTurma();

        when(mapper.toModel(dto))
                .thenReturn(model);

        when(repository.save(model))
                .thenReturn(model);

        when(mapper.toResponse(model))
                .thenReturn(
                        mock(MatriculaTurmaResponseDTO.class)
                );

        service.saveMatricula(dto);

        verify(repository, times(1))
                .save(model);
    }

    private MatriculaTurmaRequestDTO criarDto() {

        return new MatriculaTurmaRequestDTO(
                1L,
                2L,
                LocalDate.now()
        );
    }
}