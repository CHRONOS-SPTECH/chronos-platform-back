package chronos.tech.ServiceTest;

import chronos.tech.application.dto.turma.TurmaRequestDTO;
import chronos.tech.application.dto.pessoa.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.turma.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;
import chronos.tech.application.mapper.TurmaMapper;
import chronos.tech.application.service.TurmaService;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.TurmaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @Mock
    private TurmaRepository repository;

    @Mock
    private TurmaMapper mapper;

    @Mock
    private MatriculaTurmaRepository matriculaTurmaRepository;

    @Mock
    private ChamadaAulaRepository chamadaAulaRepository;

    @InjectMocks
    private TurmaService service;

    @Test
    void deveRetornarTodasAsTurmas() {

        Turma turma = new Turma();
        turma.setNomeTurma("Turma Aristóteles");
        turma.setStatusTurma(StatusTurma.EM_ANDAMENTO);

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(1, "Turma Aristóteles", LocalDate.of(2025, 1, 10), null, StatusTurma.EM_ANDAMENTO, 0.0);

        when(repository.findAll()).thenReturn(List.of(turma));
        when(mapper.toResponse(turma)).thenReturn(responseDTO);

        List<TurmaResponseDTO> resultado = service.getAllTurmas();

        assertEquals(1, resultado.size());
        assertEquals("Turma Aristóteles", resultado.get(0).nome_turma());

        verify(repository).findAll();
        verify(mapper).toResponse(turma);
    }

    @Test
    void deveRetornarTurmaPorId() {

        Long id = 1L;

        Turma turma = new Turma();

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(1, "Turma Aristóteles", LocalDate.of(2025, 1, 10), null, StatusTurma.EM_ANDAMENTO, 0.0);

        when(repository.findById(id)).thenReturn(Optional.of(turma));
        when(mapper.toResponse(turma)).thenReturn(responseDTO);

        TurmaResponseDTO resultado = service.getTurma(id);

        assertEquals(1, resultado.id_turma());
        assertEquals("Turma Aristóteles", resultado.nome_turma());
        assertEquals(StatusTurma.EM_ANDAMENTO, resultado.status_turma());

        verify(repository).findById(id);
        verify(mapper).toResponse(turma);
    }

    @Test
    void deveLancarExcecaoQuandoTurmaNaoEncontrada() {

        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getTurma(id));

        assertTrue(exception.getMessage().contains("Turma não encontrada"));

        verify(repository).findById(id);
    }

    @Test
    void deveSalvarTurma() {

        TurmaRequestDTO requestDTO = new TurmaRequestDTO("Turma Aristóteles", LocalDate.of(2025, 1, 10), null, StatusTurma.NAO_INICIADA);

        Turma turma = new Turma();
        turma.setNomeTurma("Turma Aristóteles");
        turma.setStatusTurma(StatusTurma.NAO_INICIADA);

        Turma turmaSalva = new Turma();
        turmaSalva.setIdTurma(1);
        turmaSalva.setNomeTurma("Turma Aristóteles");
        turmaSalva.setStatusTurma(StatusTurma.NAO_INICIADA);

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(1, "Turma Aristóteles", LocalDate.of(2025, 1, 10), null, StatusTurma.NAO_INICIADA, 0.0);

        when(mapper.toModel(requestDTO)).thenReturn(turma);
        when(repository.save(turma)).thenReturn(turmaSalva);
        when(mapper.toResponse(turmaSalva)).thenReturn(responseDTO);

        TurmaResponseDTO resultado = service.saveTurma(requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.id_turma());
        assertEquals(StatusTurma.NAO_INICIADA, resultado.status_turma());

        verify(mapper).toModel(requestDTO);
        verify(repository).save(turma);
        verify(mapper).toResponse(turmaSalva);
    }


    @Test
    void deveAtualizarTurma() {

        Long id = 1L;

        Turma turmaExistente = new Turma();
        turmaExistente.setIdTurma(1);
        turmaExistente.setNomeTurma("Turma Antiga");
        turmaExistente.setStatusTurma(StatusTurma.NAO_INICIADA);

        TurmaRequestDTO requestDTO = new TurmaRequestDTO("Turma Atualizada", LocalDate.now(), null, null);

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(1, "Turma Atualizada", LocalDate.now(), null, StatusTurma.NAO_INICIADA, 0.0);

        when(repository.findById(id)).thenReturn(Optional.of(turmaExistente));
        when(repository.save(turmaExistente)).thenReturn(turmaExistente);
        when(mapper.toResponse(turmaExistente)).thenReturn(responseDTO);

        TurmaResponseDTO resultado = service.updateTurma(id, requestDTO);

        assertEquals("Turma Atualizada", resultado.nome_turma());
        assertEquals(StatusTurma.NAO_INICIADA, resultado.status_turma());

        verify(repository).findById(id);
        verify(mapper).updateFromDto(requestDTO, turmaExistente);
        verify(repository).save(turmaExistente);
        verify(mapper).toResponse(turmaExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarTurmaInexistente() {

        Long id = 1L;

        TurmaRequestDTO requestDTO = new TurmaRequestDTO("Turma Atualizada", LocalDate.now(), null, StatusTurma.EM_ANDAMENTO);

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.updateTurma(id, requestDTO));

        assertTrue(exception.getMessage().contains("Turma não encontrada"));

        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveExcluirTurma() {

        Long id = 1L;

        Turma turma = new Turma();
        turma.setIdTurma(1);
        turma.setStatusTurma(StatusTurma.EM_ANDAMENTO);

        when(repository.findById(id)).thenReturn(Optional.of(turma));

        TurmaDeletadaResponseDTO resultado = service.deleteTurma(id);

        assertEquals("Turma desativada com sucesso", resultado.mensagem());

        assertEquals(id, resultado.id());

        assertEquals(StatusTurma.INATIVA, resultado.status_turma());

        verify(repository).findById(id);
        verify(repository).save(turma);
    }

    @Test
    void deveRetornarAlunosComPercentualPresenca() {
        Integer turmaId = 1;
        Turma turma = new Turma();
        turma.setIdTurma(turmaId);
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(10);
        pessoa.setNome("João Silva");
        pessoa.setCpf("12345678900");
        pessoa.setGenero("Masculino");
        pessoa.setEmail("joao@email.com");
        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);
        ChamadaAula presente = new ChamadaAula();
        presente.setPessoa(pessoa);
        presente.setCompareceu(true);
        ChamadaAula falta = new ChamadaAula();
        falta.setPessoa(pessoa);
        falta.setCompareceu(false);
        PageRequest pageable = PageRequest.of(0, 20);
        when(repository.findById(1L)).thenReturn(Optional.of(turma));
        when(matriculaTurmaRepository.findByTurmaIdTurma(eq(turmaId), any())).thenReturn(new PageImpl<>(List.of(matricula), pageable, 1));
        when(chamadaAulaRepository.findByPessoaIdPessoaInAndAulaTurmaIdTurma(List.of(10), turmaId)).thenReturn(List.of(presente, falta));
        Page<AlunoComPresencaResponseDTO> resultado = service.getAlunosDaTurmaComPresenca(turmaId, pageable);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("João Silva", resultado.getContent().get(0).nome());
        assertEquals(50.0, resultado.getContent().get(0).percentual_presenca());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoTurmaNaoPossuiMatriculas() {
        Integer turmaId = 1;
        Turma turma = new Turma();
        turma.setIdTurma(turmaId);
        PageRequest pageable = PageRequest.of(0, 20);
        when(repository.findById(1L)).thenReturn(Optional.of(turma));
        when(matriculaTurmaRepository.findByTurmaIdTurma(eq(turmaId), any())).thenReturn(Page.empty(pageable));
        Page<AlunoComPresencaResponseDTO> resultado = service.getAlunosDaTurmaComPresenca(turmaId, pageable);
        assertTrue(resultado.isEmpty());
        verify(chamadaAulaRepository).findByPessoaIdPessoaInAndAulaTurmaIdTurma(List.of(), turmaId);
    }
}
