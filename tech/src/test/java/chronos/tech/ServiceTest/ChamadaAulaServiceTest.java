package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.ChamadaAlunoRequestDTO;
import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.request.ListaChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.mapper.ChamadaAulaMapper;
import chronos.tech.application.service.ChamadaAulaService;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChamadaAulaServiceTest{

    @Mock
    private ChamadaAulaRepository repository;

    @Mock
    private ChamadaAulaMapper mapper;

    @Mock
    private AulaRepository aulaRepository;

    @InjectMocks
    private ChamadaAulaService service;

    @Test
    void deveSalvarChamada() {

        ChamadaAulaRequestDTO dto =
                new ChamadaAulaRequestDTO(
                        1L,
                        2L,
                        true
                );

        ChamadaAula model =
                new ChamadaAula();

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(mapper.toModel(dto))
                .thenReturn(model);

        when(repository.save(model))
                .thenReturn(model);

        when(mapper.toResponse(model))
                .thenReturn(response);

        ChamadaAulaResponseDTO resultado =
                service.saveChamada(dto);

        assertNotNull(resultado);

        verify(repository).save(model);
    }

    @Test
    void deveBuscarChamadaPorId() {

        ChamadaAula chamada =
                new ChamadaAula();

        ChamadaAulaResponseDTO response =
                mock(ChamadaAulaResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(chamada));

        when(mapper.toResponse(chamada))
                .thenReturn(response);

        ChamadaAulaResponseDTO resultado =
                service.getChamada(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoQuandoChamadaNaoExistir() {

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getChamada(1L)
                );

        assertEquals(
                "Chamada nao encontrada: 1",
                exception.getMessage()
        );
    }

    @Test
    void deveListarChamadas() {

        ChamadaAula c1 = new ChamadaAula();
        ChamadaAula c2 = new ChamadaAula();

        when(repository.findAll())
                .thenReturn(List.of(c1, c2));

        when(mapper.toResponse(any()))
                .thenReturn(mock(ChamadaAulaResponseDTO.class));

        List<ChamadaAulaResponseDTO> resultado =
                service.getAllChamadas();

        assertEquals(2, resultado.size());
    }

    @Test
    void deveRetornarListaVazia() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<ChamadaAulaResponseDTO> resultado =
                service.getAllChamadas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarChamada() {

        ChamadaAulaRequestDTO dto =
                new ChamadaAulaRequestDTO(
                        1L,
                        2L,
                        true
                );

        ChamadaAula chamada =
                new ChamadaAula();

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(chamada));

        when(repository.save(chamada))
                .thenReturn(chamada);

        when(mapper.toResponse(chamada))
                .thenReturn(mock(ChamadaAulaResponseDTO.class));

        service.updateChamada(1L, dto);

        verify(mapper)
                .updateFromDto(dto, chamada);
    }

    @Test
    void naoDeveAtualizarChamadaInexistente() {

        ChamadaAulaRequestDTO dto =
                new ChamadaAulaRequestDTO(
                        1L,
                        2L,
                        true
                );

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.updateChamada(1L, dto)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void deveDeletarChamada() {

        service.deleteChamada(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deveSalvarListaDeChamada() {

        ChamadaAlunoRequestDTO aluno1 =
                new ChamadaAlunoRequestDTO(
                        1L,
                        true
                );

        ChamadaAlunoRequestDTO aluno2 =
                new ChamadaAlunoRequestDTO(
                        2L,
                        false
                );

        ListaChamadaAulaRequestDTO dto =
                new ListaChamadaAulaRequestDTO(
                        10,
                        List.of(aluno1, aluno2)
                );

        ChamadaAula chamada1 =
                new ChamadaAula();

        ChamadaAula chamada2 =
                new ChamadaAula();

        when(mapper.toModel(any()))
                .thenReturn(chamada1)
                .thenReturn(chamada2);

        when(repository.saveAll(anyList()))
                .thenReturn(List.of(chamada1, chamada2));

        when(mapper.toResponse(any()))
                .thenReturn(mock(ChamadaAulaResponseDTO.class));

        List<ChamadaAulaResponseDTO> resultado =
                service.saveListaChamada(dto);

        assertEquals(2, resultado.size());

        verify(repository)
                .saveAll(anyList());
    }

    @Test
    void deveSalvarListaVazia() {

        ListaChamadaAulaRequestDTO dto =
                new ListaChamadaAulaRequestDTO(
                        10,
                        List.of()
                );

        when(repository.saveAll(anyList()))
                .thenReturn(List.of());

        List<ChamadaAulaResponseDTO> resultado =
                service.saveListaChamada(dto);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarChamadasPorAula() {

        ChamadaAula chamada =
                new ChamadaAula();

        when(repository.findByAulaIdAula(10L))
                .thenReturn(List.of(chamada));

        when(mapper.toResponse(chamada))
                .thenReturn(mock(ChamadaAulaResponseDTO.class));

        List<ChamadaAulaResponseDTO> resultado =
                service.getChamadasByAula(10L);

        assertEquals(1, resultado.size());
    }

    @Test
    void deveRetornarListaVaziaAoBuscarChamadasPorAula() {

        when(repository.findByAulaIdAula(10L))
                .thenReturn(List.of());

        List<ChamadaAulaResponseDTO> resultado =
                service.getChamadasByAula(10L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void devePropagarErroAoSalvarLista() {

        ChamadaAlunoRequestDTO aluno =
                new ChamadaAlunoRequestDTO(
                        1L,
                        true
                );

        ListaChamadaAulaRequestDTO dto =
                new ListaChamadaAulaRequestDTO(
                        1,
                        List.of(aluno)
                );

        when(mapper.toModel(any()))
                .thenReturn(new ChamadaAula());

        when(repository.saveAll(anyList()))
                .thenThrow(
                        new RuntimeException("Erro banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.saveListaChamada(dto)
                );

        assertEquals(
                "Erro banco",
                exception.getMessage()
        );
    }


}
