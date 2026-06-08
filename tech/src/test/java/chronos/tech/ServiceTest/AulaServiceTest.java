package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.request.MovimentacaoAulaDTO;
import chronos.tech.application.dto.response.*;
import chronos.tech.application.mapper.AulaMapper;
import chronos.tech.application.mapper.MateriaMapper;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.mapper.TemaAulaMapper;
import chronos.tech.application.service.AulaService;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TemaAula;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository repository;

    @Mock
    private AulaMapper mapper;

    @Mock
    private MatriculaTurmaRepository matriculaTurmaRepository;

    @Mock
    private ChamadaAulaRepository chamadaAulaRepository;

    @Mock
    private TemaAulaMapper temaMapper;

    @Mock
    private MateriaMapper materiaMapper;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TemaAulaRepository temaAulaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @InjectMocks
    private AulaService service;

    private Aula aula;
    private AulaResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        aula = new Aula();
        aula.setIdAula(1);

        responseDTO = new AulaResponseDTO(
                1,
                null,
                null,
                null,
                null,
                null,
                1L,
                1L,
                1L
        );
    }

    @Test
    void deveRetornarAulaQuandoIdExistir() {

        when(repository.findById(1))
                .thenReturn(Optional.of(aula));

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        AulaResponseDTO resultado = service.getAula(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.id_aula());

        verify(repository).findById(1);
        verify(mapper).toResponse(aula);
    }

    @Test
    void deveLancarExcecaoQuandoAulaNaoExistir() {

        when(repository.findById(1))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.getAula(1)
        );

        assertEquals("Aula nao encontrada: 1", ex.getMessage());
    }

    @Test
    void deveSalvarAula() {

        AulaRequestDTO request = mock(AulaRequestDTO.class);

        when(mapper.toModel(request))
                .thenReturn(aula);

        when(repository.save(aula))
                .thenReturn(aula);

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        AulaResponseDTO resultado = service.saveAula(request);

        assertNotNull(resultado);

        verify(repository).save(aula);
    }

    @Test
    void deveAtualizarAula() {

        AulaRequestDTO request = mock(AulaRequestDTO.class);

        when(repository.findById(1))
                .thenReturn(Optional.of(aula));

        when(repository.save(aula))
                .thenReturn(aula);

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        AulaResponseDTO resultado = service.updateAula(1, request);

        assertNotNull(resultado);

        verify(mapper).updateFromDto(request, aula);
        verify(repository).save(aula);
    }

    @Test
    void deveExcluirAula() {

        service.deleteAula(1);

        verify(repository).deleteById(1);
    }

    @Test
    void deveRetornarListaDeAulas() {

        when(repository.findAll())
                .thenReturn(List.of(aula));

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        List<AulaResponseDTO> resultado = service.getAllAulas();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deveRetornarAulasDoDia() {

        TemaAula tema = mock(TemaAula.class);

        aula.setTema(tema);

        when(repository.findByDataAulaAndInstrutorIdPessoa(any(), eq(1)))
                .thenReturn(List.of(aula));

        when(chamadaAulaRepository.existsByAula(aula))
                .thenReturn(true);

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        when(temaMapper.toResponse(any()))
                .thenReturn(mock(TemaAulaResponseDTO.class));

        when(materiaMapper.toResponse(any()))
                .thenReturn(mock(MateriaResponseDTO.class));

        List<AulaComTemaEMateriaResponseDTO> resultado =
                service.getAulasDoDia(Date.valueOf("2025-06-01"), 1);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getChamadaFeita());
    }

    @Test
    void deveRetornarAulaCompletaPorId() {

        TemaAula tema = mock(TemaAula.class);

        aula.setTema(tema);

        when(repository.findById(1))
                .thenReturn(Optional.of(aula));

        when(chamadaAulaRepository.existsByAula(aula))
                .thenReturn(false);

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        when(temaMapper.toResponse(any()))
                .thenReturn(mock(TemaAulaResponseDTO.class));

        when(materiaMapper.toResponse(any()))
                .thenReturn(mock(MateriaResponseDTO.class));

        AulaComTemaEMateriaResponseDTO resultado =
                service.getAulaComTemaEMateriaPorId(1);

        assertNotNull(resultado);
        assertFalse(resultado.getChamadaFeita());
    }

    @Test
    void deveRetornarAulasPorTurma() {

        TemaAula tema = mock(TemaAula.class);

        aula.setTema(tema);

        when(repository.findByTurmaIdTurma(1))
                .thenReturn(List.of(aula));

        when(chamadaAulaRepository.existsByAula(aula))
                .thenReturn(true);

        when(mapper.toResponse(aula))
                .thenReturn(responseDTO);

        when(temaMapper.toResponse(any()))
                .thenReturn(mock(TemaAulaResponseDTO.class));

        when(materiaMapper.toResponse(any()))
                .thenReturn(mock(MateriaResponseDTO.class));

        when(pessoaMapper.toResumidoResponse(any()))
                .thenReturn(mock(PessoaResumidoResponseDTO.class));

        List<AulaComTemaEMateriaComInstrutorResponseDTO> resultado =
                service.getAulasPorTurma(1);

        assertEquals(1, resultado.size());
    }

    @Test
    void deveRemanejarAulaComSucesso() {

        Pessoa professor = new Pessoa();
        professor.setIdPessoa(10);

        Turma turma = new Turma();
        turma.setIdTurma(20);

        aula.setInstrutor(professor);
        aula.setTurma(turma);

        MovimentacaoAulaDTO mov =
                new MovimentacaoAulaDTO(
                        1,
                        "2025-06-20",
                        "08:00"
                );

        when(repository.findById(1))
                .thenReturn(Optional.of(aula));

        when(repository.findByInstrutorIdPessoaAndDataAulaAndIdAulaNot(
                anyInt(), any(), anyInt()))
                .thenReturn(List.of());

        when(repository.findByTurmaIdTurmaAndDataAulaAndIdAulaNot(
                anyInt(), any(), anyInt()))
                .thenReturn(List.of());

        service.remanejarAulasEmLote(List.of(mov));

        verify(repository).save(aula);

        assertNotNull(aula.getDataAula());
        assertNotNull(aula.getHoraInicio());
        assertNotNull(aula.getHoraFim());
    }

    @Test
    void deveLancarExcecaoQuandoProfessorPossuirConflito() {

        Pessoa professor = new Pessoa();
        professor.setIdPessoa(1);
        professor.setNome("João");

        aula.setInstrutor(professor);
        aula.setIdAula(1);

        Aula conflito = new Aula();
        conflito.setHoraInicio(Time.valueOf("08:00:00"));
        conflito.setHoraFim(Time.valueOf("10:00:00"));

        MovimentacaoAulaDTO mov =
                new MovimentacaoAulaDTO(
                        1,
                        "2025-06-20",
                        "09:00"
                );

        when(repository.findById(1))
                .thenReturn(Optional.of(aula));

        when(repository.findByInstrutorIdPessoaAndDataAulaAndIdAulaNot(
                anyInt(), any(), anyInt()))
                .thenReturn(List.of(conflito));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.remanejarAulasEmLote(List.of(mov))
        );

        assertTrue(ex.getMessage().contains("Conflito"));
    }



}