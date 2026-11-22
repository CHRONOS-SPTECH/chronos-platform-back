package chronos.tech.ServiceTest;

import chronos.tech.application.dto.turma.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.turma.MatriculaTurmaResponseDTO;
import chronos.tech.application.mapper.MatriculaTurmaMapper;
import chronos.tech.application.service.MatriculaTurmaService;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TurmaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

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

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TurmaRepository turmaRepository;

    @InjectMocks
    private MatriculaTurmaService service;

    @Test
    void deveSalvarMatriculaComSucesso() {
        MatriculaTurmaRequestDTO dto = criarDto();
        Pessoa pessoa = pessoaValida();
        Turma turma = turmaValida();
        MatriculaTurma model = new MatriculaTurma();
        MatriculaTurma matriculaSalva = new MatriculaTurma();
        MatriculaTurmaResponseDTO response = mock(MatriculaTurmaResponseDTO.class);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());
        when(mapper.toModel(dto)).thenReturn(model);
        when(repository.save(model)).thenReturn(matriculaSalva);
        when(mapper.toResponse(matriculaSalva)).thenReturn(response);

        MatriculaTurmaResponseDTO resultado = service.saveMatricula(dto);

        assertNotNull(resultado);
        verify(mapper).toModel(dto);
        verify(repository).save(model);
        verify(mapper).toResponse(matriculaSalva);
    }

    @Test
    void deveBuscarMatriculaPorId() {
        MatriculaTurma matricula = new MatriculaTurma();
        MatriculaTurmaResponseDTO response = mock(MatriculaTurmaResponseDTO.class);

        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(matricula));
        when(mapper.toResponse(matricula)).thenReturn(response);

        MatriculaTurmaResponseDTO resultado = service.getMatricula(1, 1);

        assertNotNull(resultado);
        verify(repository).findById(new MatriculaTurmaId(1, 1));
    }

    @Test
    void deveLancarExcecaoQuandoMatriculaNaoExistir() {
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.getMatricula(1, 1)
        );

        assertTrue(exception.getReason().contains("Matrícula não encontrada"));
    }

    @Test
    void deveListarTodasAsMatriculas() {
        MatriculaTurma m1 = new MatriculaTurma();
        MatriculaTurma m2 = new MatriculaTurma();
        MatriculaTurmaResponseDTO response = mock(MatriculaTurmaResponseDTO.class);

        when(repository.findAll()).thenReturn(List.of(m1, m2));
        when(mapper.toResponse(any(MatriculaTurma.class))).thenReturn(response);

        List<MatriculaTurmaResponseDTO> resultado = service.getAllMatriculas();

        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremMatriculas() {
        when(repository.findAll()).thenReturn(List.of());

        List<MatriculaTurmaResponseDTO> resultado = service.getAllMatriculas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarMatriculaComSucesso() {
        MatriculaTurmaRequestDTO dto = new MatriculaTurmaRequestDTO(1L, 1L, LocalDate.now());
        MatriculaTurma existente = new MatriculaTurma();
        MatriculaTurmaResponseDTO response = mock(MatriculaTurmaResponseDTO.class);

        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toResponse(existente)).thenReturn(response);

        MatriculaTurmaResponseDTO resultado = service.updateMatricula(1, 1, dto);

        assertNotNull(resultado);
        verify(mapper).updateFromDto(dto, existente);
        verify(repository).save(existente);
    }

    @Test
    void naoDeveAtualizarMatriculaInexistente() {
        MatriculaTurmaRequestDTO dto = new MatriculaTurmaRequestDTO(1L, 1L, LocalDate.now());

        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.updateMatricula(1, 1, dto)
        );

        assertTrue(exception.getReason().contains("Matrícula não encontrada"));
    }

    @Test
    void naoDeveSalvarQuandoMatriculaNaoExistirNoUpdate() {
        MatriculaTurmaRequestDTO dto = new MatriculaTurmaRequestDTO(1L, 1L, LocalDate.now());

        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateMatricula(1, 1, dto));
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarMatricula() {
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(new MatriculaTurma()));

        service.deleteMatricula(1, 1);

        verify(repository).deleteById(new MatriculaTurmaId(1, 1));
    }

    @Test
    void deveExecutarDeleteUmaUnicaVez() {
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(new MatriculaTurma()));

        service.deleteMatricula(1, 1);

        verify(repository, times(1)).deleteById(new MatriculaTurmaId(1, 1));
    }

    @Test
    void devePropagarErroAoSalvarMatricula() {
        MatriculaTurmaRequestDTO dto = criarDto();
        Pessoa pessoa = pessoaValida();
        Turma turma = turmaValida();
        MatriculaTurma model = new MatriculaTurma();

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());
        when(mapper.toModel(dto)).thenReturn(model);
        when(repository.save(model)).thenThrow(new RuntimeException("Erro no banco"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveMatricula(dto)
        );

        assertEquals("Erro no banco", exception.getMessage());
    }

    @Test
    void devePropagarErroAoListarMatriculas() {
        when(repository.findAll()).thenThrow(new RuntimeException("Falha banco"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getAllMatriculas()
        );

        assertEquals("Falha banco", exception.getMessage());
    }

    @Test
    void devePropagarErroAoBuscarMatricula() {
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenThrow(new RuntimeException("Banco indisponivel"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getMatricula(1, 1)
        );

        assertEquals("Banco indisponivel", exception.getMessage());
    }

    @Test
    void devePropagarErroAoDeletarMatricula() {
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(new MatriculaTurma()));
        doThrow(new RuntimeException("Erro ao deletar")).when(repository).deleteById(new MatriculaTurmaId(1, 1));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.deleteMatricula(1, 1)
        );

        assertEquals("Erro ao deletar", exception.getMessage());
    }

    @Test
    void deveChamarUpdateFromDtoUmaUnicaVez() {
        MatriculaTurmaRequestDTO dto = new MatriculaTurmaRequestDTO(1L, 1L, LocalDate.now());
        MatriculaTurma existente = new MatriculaTurma();

        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toResponse(existente)).thenReturn(mock(MatriculaTurmaResponseDTO.class));

        service.updateMatricula(1, 1, dto);

        verify(mapper, times(1)).updateFromDto(dto, existente);
    }

    @Test
    void deveChamarSaveUmaUnicaVezAoCriarMatricula() {
        MatriculaTurmaRequestDTO dto = criarDto();
        Pessoa pessoa = pessoaValida();
        Turma turma = turmaValida();
        MatriculaTurma model = new MatriculaTurma();

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(repository.findById(new MatriculaTurmaId(1, 1))).thenReturn(Optional.empty());
        when(mapper.toModel(dto)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(mock(MatriculaTurmaResponseDTO.class));

        service.saveMatricula(dto);

        verify(repository, times(1)).save(model);
    }

    private MatriculaTurmaRequestDTO criarDto() {
        return new MatriculaTurmaRequestDTO(1L, 1L, LocalDate.now());
    }

    private Pessoa pessoaValida() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setDataSaida(null);
        TipoVinculo tipo = new TipoVinculo();
        tipo.setNome_vinculo("Membro");
        pessoa.setTipoVinculo(tipo);
        return pessoa;
    }

    private Turma turmaValida() {
        Turma turma = new Turma();
        turma.setIdTurma(1);
        turma.setStatusTurma(StatusTurma.EM_ANDAMENTO);
        return turma;
    }
}

