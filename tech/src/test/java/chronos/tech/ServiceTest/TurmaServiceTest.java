package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
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

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(
                1,
                "Turma Aristóteles",
                LocalDate.of(2025, 1, 10),
                null,
                StatusTurma.EM_ANDAMENTO,
                0.0
        );

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

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(
                1,
                "Turma Aristóteles",
                LocalDate.of(2025, 1, 10),
                null,
                StatusTurma.EM_ANDAMENTO,
                0.0
        );

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

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getTurma(id)
        );

        assertEquals(
                "Turma não encontrado com o ID: 1",
                exception.getMessage()
        );

        verify(repository).findById(id);
    }

    @Test
    void deveSalvarTurma() {

        TurmaRequestDTO requestDTO = new TurmaRequestDTO(
                "Turma Aristóteles",
                LocalDate.of(2025, 1, 10),
                null,
                StatusTurma.NAO_INICIADA
        );

        Turma turma = new Turma();
        turma.setNomeTurma("Turma Aristóteles");
        turma.setStatusTurma(StatusTurma.NAO_INICIADA);

        Turma turmaSalva = new Turma();
        turmaSalva.setIdTurma(1);
        turmaSalva.setNomeTurma("Turma Aristóteles");
        turmaSalva.setStatusTurma(StatusTurma.NAO_INICIADA);

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(
                1,
                "Turma Aristóteles",
                LocalDate.of(2025, 1, 10),
                null,
                StatusTurma.NAO_INICIADA,
                0.0
        );

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

        TurmaRequestDTO requestDTO = new TurmaRequestDTO(
                "Turma Atualizada",
                LocalDate.now(),
                null,
                StatusTurma.EM_ANDAMENTO
        );

        TurmaResponseDTO responseDTO = new TurmaResponseDTO(
                1,
                "Turma Atualizada",
                LocalDate.now(),
                null,
                StatusTurma.EM_ANDAMENTO,
                0.0
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(turmaExistente));

        when(repository.save(turmaExistente))
                .thenReturn(turmaExistente);

        when(mapper.toResponse(turmaExistente))
                .thenReturn(responseDTO);

        TurmaResponseDTO resultado =
                service.updateTurma(id, requestDTO);

        assertEquals("Turma Atualizada", resultado.nome_turma());
        assertEquals(StatusTurma.EM_ANDAMENTO, resultado.status_turma());

        verify(repository).findById(id);
        verify(mapper).updateFromDto(requestDTO, turmaExistente);
        verify(repository).save(turmaExistente);
        verify(mapper).toResponse(turmaExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarTurmaInexistente() {

        Long id = 1L;

        TurmaRequestDTO requestDTO = new TurmaRequestDTO(
                "Turma Atualizada",
                LocalDate.now(),
                null,
                StatusTurma.EM_ANDAMENTO
        );

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.updateTurma(id, requestDTO)
        );

        assertEquals(
                "Turma não encontrado com o ID: 1",
                exception.getMessage()
        );

        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveExcluirTurma() {

        Long id = 1L;

        Turma turma = new Turma();
        turma.setIdTurma(1);
        turma.setStatusTurma(StatusTurma.EM_ANDAMENTO);

        when(repository.findById(id))
                .thenReturn(Optional.of(turma));

        TurmaDeletadaResponseDTO resultado =
                service.deleteTurma(id);

        assertEquals(
                "Turma desativada com sucesso",
                resultado.mensagem()
        );

        assertEquals(id, resultado.id());

        assertEquals(
                StatusTurma.INATIVA,
                resultado.status_turma()
        );

        verify(repository).findById(id);
        verify(repository).save(turma);
    }

    @Test
    void deveRetornarAlunosComPercentualPresenca() {

        Integer turmaId = 1;

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(10);
        pessoa.setNome("João Silva");
        pessoa.setCpf("12345678900");
        pessoa.setGenero("Masculino");
        pessoa.setEmail("joao@email.com");

        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);

        ChamadaAula chamada1 = new ChamadaAula();
        chamada1.setCompareceu(true);

        ChamadaAula chamada2 = new ChamadaAula();
        chamada2.setCompareceu(true);

        ChamadaAula chamada3 = new ChamadaAula();
        chamada3.setCompareceu(false);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(10, turmaId))
                .thenReturn(List.of(chamada1, chamada2, chamada3));

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(1, resultado.size());

        AlunoComPresencaResponseDTO aluno = resultado.get(0);

        assertEquals(10, aluno.id_pessoa());
        assertEquals("João Silva", aluno.nome());
        assertEquals("12345678900", aluno.cpf());
        assertEquals("Masculino", aluno.genero());
        assertEquals("joao@email.com", aluno.email());
        assertEquals(66.66666666666667, aluno.percentual_presenca());

        verify(matriculaTurmaRepository)
                .findByTurmaIdTurma(turmaId);

        verify(chamadaAulaRepository)
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(10, turmaId);
    }

    @Test
    void deveRetornarPresencaZeroQuandoAlunoNaoPossuiChamadas() {

        Integer turmaId = 1;

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(20);
        pessoa.setNome("Maria");
        pessoa.setCpf("99999999999");
        pessoa.setGenero("Feminino");
        pessoa.setEmail("maria@email.com");

        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(20, turmaId))
                .thenReturn(List.of());

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(1, resultado.size());

        AlunoComPresencaResponseDTO aluno = resultado.get(0);

        assertEquals(20, aluno.id_pessoa());
        assertEquals("Maria", aluno.nome());
        assertEquals(0.0, aluno.percentual_presenca());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremTurmas() {

        when(repository.findAll()).thenReturn(List.of());

        List<TurmaResponseDTO> resultado = service.getAllTurmas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void deveRetornarDoisAlunosComPresenca() {

        Integer turmaId = 1;

        Pessoa aluno1 = new Pessoa();
        aluno1.setIdPessoa(1);
        aluno1.setNome("João");
        aluno1.setCpf("111");
        aluno1.setGenero("Masculino");
        aluno1.setEmail("joao@email.com");

        Pessoa aluno2 = new Pessoa();
        aluno2.setIdPessoa(2);
        aluno2.setNome("Maria");
        aluno2.setCpf("222");
        aluno2.setGenero("Feminino");
        aluno2.setEmail("maria@email.com");

        MatriculaTurma matricula1 = new MatriculaTurma();
        matricula1.setPessoa(aluno1);

        MatriculaTurma matricula2 = new MatriculaTurma();
        matricula2.setPessoa(aluno2);

        ChamadaAula chamada1 = new ChamadaAula();
        chamada1.setCompareceu(true);

        ChamadaAula chamada2 = new ChamadaAula();
        chamada2.setCompareceu(false);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula1, matricula2));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(1, turmaId))
                .thenReturn(List.of(chamada1, chamada2));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(2, turmaId))
                .thenReturn(List.of(chamada1));

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(2, resultado.size());

        assertEquals("João", resultado.get(0).nome());
        assertEquals(50.0, resultado.get(0).percentual_presenca());

        assertEquals("Maria", resultado.get(1).nome());
        assertEquals(100.0, resultado.get(1).percentual_presenca());
    }

    @Test
    void deveCalcularPresencaTotal() {

        Integer turmaId = 1;

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(10);
        pessoa.setNome("Carlos");

        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);

        ChamadaAula chamada1 = new ChamadaAula();
        chamada1.setCompareceu(true);

        ChamadaAula chamada2 = new ChamadaAula();
        chamada2.setCompareceu(true);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(10, turmaId))
                .thenReturn(List.of(chamada1, chamada2));

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(100.0,
                resultado.get(0).percentual_presenca());
    }

    @Test
    void deveCalcularPresencaZeroQuandoTodasFaltas() {

        Integer turmaId = 1;

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(10);
        pessoa.setNome("Carlos");

        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);

        ChamadaAula chamada1 = new ChamadaAula();
        chamada1.setCompareceu(false);

        ChamadaAula chamada2 = new ChamadaAula();
        chamada2.setCompareceu(false);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(10, turmaId))
                .thenReturn(List.of(chamada1, chamada2));

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(0.0,
                resultado.get(0).percentual_presenca());
    }

    @Test
    void deveIgnorarCompareceuNuloNoCalculoDePresenca() {

        Integer turmaId = 1;

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(10);

        MatriculaTurma matricula = new MatriculaTurma();
        matricula.setPessoa(pessoa);

        ChamadaAula chamada1 = new ChamadaAula();
        chamada1.setCompareceu(true);

        ChamadaAula chamada2 = new ChamadaAula();
        chamada2.setCompareceu(null);

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of(matricula));

        when(chamadaAulaRepository
                .findByPessoaIdPessoaAndAulaTurmaIdTurma(10, turmaId))
                .thenReturn(List.of(chamada1, chamada2));

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertEquals(
                50.0,
                resultado.get(0).percentual_presenca()
        );
    }

    @Test
    void deveRetornarListaVaziaQuandoTurmaNaoPossuiMatriculas() {

        Integer turmaId = 1;

        when(matriculaTurmaRepository.findByTurmaIdTurma(turmaId))
                .thenReturn(List.of());

        List<AlunoComPresencaResponseDTO> resultado =
                service.getAlunosDaTurmaComPresenca(turmaId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(matriculaTurmaRepository)
                .findByTurmaIdTurma(turmaId);

        verifyNoInteractions(chamadaAulaRepository);
    }



}