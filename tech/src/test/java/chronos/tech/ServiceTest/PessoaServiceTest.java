package chronos.tech.ServiceTest;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.service.PessoaService;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.port.PessoaRepository;
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
class PessoaServiceTest {

    @Mock
    private PessoaRepository repository;

    @Mock
    private PessoaMapper mapper;

    @InjectMocks
    private PessoaService service;

    @Test
    void deveCriarPessoaComSucesso() {

        PessoaRequestDTO dto = criarPessoaDTO();

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");

        Pessoa pessoaSalva = new Pessoa();
        pessoaSalva.setIdPessoa(1);
        pessoaSalva.setNome("João");

        PessoaResponseDTO response = mock(PessoaResponseDTO.class);

        when(mapper.toModel(dto)).thenReturn(pessoa);
        when(repository.save(pessoa)).thenReturn(pessoaSalva);
        when(mapper.toResponse(pessoaSalva)).thenReturn(response);

        PessoaResponseDTO resultado = service.createPessoa(dto);

        assertNotNull(resultado);

        verify(mapper).toModel(dto);
        verify(repository).save(pessoa);
        verify(mapper).toResponse(pessoaSalva);
    }

    @Test
    void deveBuscarPessoaPorId() {

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        PessoaResponseDTO response = mock(PessoaResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(pessoa));

        when(mapper.toResponse(pessoa))
                .thenReturn(response);

        PessoaResponseDTO resultado =
                service.pegarPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoAoBuscarPessoaInexistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.pegarPorId(99L)
                );

        assertEquals(
                "Não foi possível achar",
                exception.getMessage()
        );
    }

    @Test
    void deveListarTodasAsPessoas() {

        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();

        PessoaResponseDTO response = mock(PessoaResponseDTO.class);

        when(repository.findAll())
                .thenReturn(List.of(pessoa1, pessoa2));

        when(mapper.toResponse(any(Pessoa.class)))
                .thenReturn(response);

        List<PessoaResponseDTO> resultado =
                service.getAllPersons();

        assertEquals(2, resultado.size());
    }

    @Test
    void deveAtualizarPessoa() {

        PessoaRequestDTO dto = criarPessoaDTO();

        Pessoa existente = new Pessoa();
        existente.setIdPessoa(1);

        PessoaResponseDTO response = mock(PessoaResponseDTO.class);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(existente))
                .thenReturn(existente);

        when(mapper.toResponse(existente))
                .thenReturn(response);

        PessoaResponseDTO resultado =
                service.updatePessoa(1L, dto);

        assertNotNull(resultado);

        verify(mapper)
                .updateFromDto(dto, existente);

        verify(repository)
                .save(existente);
    }

    @Test
    void naoDeveAtualizarPessoaInexistente() {

        PessoaRequestDTO dto = criarPessoaDTO();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.updatePessoa(1L, dto)
                );

        assertTrue(
                exception.getMessage()
                        .contains("Pessoa não encontrada")
        );
    }

    @Test
    void deveDeletarPessoa() {

        service.deletePessoa(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremPessoas() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<PessoaResponseDTO> resultado =
                service.getAllPersons();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void devePropagarErroAoSalvarPessoa() {

        PessoaRequestDTO dto = criarPessoaDTO();

        Pessoa pessoa = new Pessoa();

        when(mapper.toModel(dto))
                .thenReturn(pessoa);

        when(repository.save(pessoa))
                .thenThrow(
                        new RuntimeException("Erro no banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.createPessoa(dto)
                );

        assertEquals(
                "Erro no banco",
                exception.getMessage()
        );
    }

    private PessoaRequestDTO criarPessoaDTO() {

        return new PessoaRequestDTO(
                "João Silva",
                "joao@email.com",
                "11999999999",
                "Masculino",
                "12345678901",
                true,
                "https://foto.com",
                1,
                LocalDate.of(2000,1,1),
                LocalDate.now(),
                null,
                null
        );
    }

    @Test
    void deveRetornarDetalhesDeTodasAsPessoas() {

        TipoVinculo vinculo = new TipoVinculo();
        vinculo.setIdTipoVinculo(1);
        vinculo.setNome_vinculo("Aluno");
        vinculo.setDescricao("Aluno regular");

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("João");
        pessoa.setTipoVinculo(vinculo);

        when(repository.findAll())
                .thenReturn(List.of(pessoa));

        List<PessoaDetalhadaResponseDTO> resultado =
                service.getAllPersonsDetails();

        assertEquals(1, resultado.size());
        assertEquals("João", resultado.get(0).nome());
    }

    @Test
    void deveRetornarListaVaziaAoBuscarDetalhes() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<PessoaDetalhadaResponseDTO> resultado =
                service.getAllPersonsDetails();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarDetalhesPorId() {

        TipoVinculo vinculo = new TipoVinculo();
        vinculo.setIdTipoVinculo(1);
        vinculo.setNome_vinculo("Professor");

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos");
        pessoa.setTipoVinculo(vinculo);

        when(repository.findById(1L))
                .thenReturn(Optional.of(pessoa));

        PessoaDetalhadaResponseDTO resultado =
                service.getPersonsDetailsId(1L);

        assertEquals("Carlos", resultado.nome());
    }

    @Test
    void deveLancarExcecaoAoBuscarDetalhesInexistentes() {

        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getPersonsDetailsId(100L)
                );

        assertEquals(
                "Não foi possível achar",
                exception.getMessage()
        );
    }

    @Test
    void deveChamarMapperUmaUnicaVezAoAtualizar() {

        PessoaRequestDTO dto = criarPessoaDTO();

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        when(repository.findById(1L))
                .thenReturn(Optional.of(pessoa));

        when(repository.save(pessoa))
                .thenReturn(pessoa);

        when(mapper.toResponse(any()))
                .thenReturn(mock(PessoaResponseDTO.class));

        service.updatePessoa(1L, dto);

        verify(mapper, times(1))
                .updateFromDto(dto, pessoa);
    }

    @Test
    void deveSalvarPessoaUmaUnicaVez() {

        PessoaRequestDTO dto = criarPessoaDTO();

        Pessoa pessoa = new Pessoa();

        when(mapper.toModel(dto))
                .thenReturn(pessoa);

        when(repository.save(pessoa))
                .thenReturn(pessoa);

        when(mapper.toResponse(pessoa))
                .thenReturn(mock(PessoaResponseDTO.class));

        service.createPessoa(dto);

        verify(repository, times(1))
                .save(pessoa);
    }

    @Test
    void naoDeveSalvarQuandoPessoaNaoExistir() {

        PessoaRequestDTO dto = criarPessoaDTO();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.updatePessoa(1L, dto)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void deveExecutarDeleteUmaUnicaVez() {

        service.deletePessoa(1L);

        verify(repository, times(1))
                .deleteById(1L);
    }

    @Test
    void devePropagarErroAoDeletarPessoa() {

        doThrow(new RuntimeException("Erro ao deletar"))
                .when(repository)
                .deleteById(1L);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.deletePessoa(1L)
                );

        assertEquals(
                "Erro ao deletar",
                exception.getMessage()
        );
    }

    @Test
    void devePropagarErroAoListarPessoas() {

        when(repository.findAll())
                .thenThrow(
                        new RuntimeException("Falha no banco")
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getAllPersons()
                );

        assertEquals(
                "Falha no banco",
                exception.getMessage()
        );
    }


}