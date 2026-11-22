package chronos.tech.ServiceTest;

import chronos.tech.application.dto.pessoa.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaFichaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.mapper.EnderecoPessoaMapper;
import chronos.tech.application.service.PessoaService;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.port.EnderecoPessoaRepository;
import chronos.tech.domain.port.HistoricoAcademicoRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TipoVinculoRepository;
import chronos.tech.domain.port.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import chronos.tech.application.exception.PessoaNaoEncontradaException;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository repository;

    @Mock
    private PessoaMapper mapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoVinculoRepository tipoVinculoRepository;

    @Mock
    private EnderecoPessoaRepository enderecoPessoaRepository;

    @Mock
    private MatriculaTurmaRepository matriculaTurmaRepository;

    @Mock
    private HistoricoAcademicoRepository historicoAcademicoRepository;

    @Mock
    private EnderecoPessoaMapper enderecoPessoaMapper;

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

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
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

        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(mapper.toResponse(pessoa)).thenReturn(response);

        PessoaResponseDTO resultado = service.pegarPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoAoBuscarPessoaInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.pegarPorId(99L)
        );

        assertTrue(exception.getMessage().contains("Pessoa não encontrada"));
    }

    @Test
    void deveListarTodasAsPessoas() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findByTipoVinculo(eq("Público Externo"), any(Pageable.class)))
                .thenReturn(Page.empty(pageable));

        Page<?> resultado = service.getAllPersons(pageable);

        assertNotNull(resultado);
    }

    @Test
    void deveAtualizarPessoa() {
        PessoaRequestDTO dto = criarPessoaDTO();
        Pessoa existente = new Pessoa();
        existente.setIdPessoa(1);
        PessoaResponseDTO response = mock(PessoaResponseDTO.class);

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toResponse(existente)).thenReturn(response);

        PessoaResponseDTO resultado = service.updatePessoa(1L, dto);

        assertNotNull(resultado);
        verify(mapper).updateFromDto(dto, existente);
        verify(repository).save(existente);
    }

    @Test
    void naoDeveAtualizarPessoaInexistente() {
        PessoaRequestDTO dto = criarPessoaDTO();

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        PessoaNaoEncontradaException exception = assertThrows(
                PessoaNaoEncontradaException.class,
                () -> service.updatePessoa(1L, dto)
        );

        assertTrue(exception.getMessage().contains("Pessoa não encontrada"));
    }

    @Test
    void deveDeletarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setDataSaida(null);

        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(usuarioRepository.findByPessoaIdPessoa(1)).thenReturn(Optional.empty());
        when(repository.save(pessoa)).thenReturn(pessoa);

        service.deletePessoa(1L);

        verify(repository).save(pessoa);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremPessoas() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findByTipoVinculo(eq("Público Externo"), any(Pageable.class)))
                .thenReturn(Page.empty(pageable));

        Page<?> resultado = service.getAllPersons(pageable);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void devePropagarErroAoSalvarPessoa() {
        PessoaRequestDTO dto = criarPessoaDTO();
        Pessoa pessoa = new Pessoa();

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(mapper.toModel(dto)).thenReturn(pessoa);
        when(repository.save(pessoa)).thenThrow(new RuntimeException("Erro no banco"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.createPessoa(dto)
        );

        assertEquals("Erro no banco", exception.getMessage());
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
                LocalDate.of(2000, 1, 1),
                LocalDate.now(),
                null,
                null,
                null
        );
    }

    @Test
    void deveRetornarDetalhesDeTodasAsPessoas() {
        Pageable pageable = PageRequest.of(0, 10);
        TipoVinculo vinculo = new TipoVinculo();
        vinculo.setIdTipoVinculo(1);
        vinculo.setNome_vinculo("Aluno");
        vinculo.setDescricao("Aluno regular");

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("João");
        pessoa.setTipoVinculo(vinculo);

        PessoaDetalhadaResponseDTO response = new PessoaDetalhadaResponseDTO(
                pessoa.getIdPessoa(), pessoa.getNome(), null, null, null, null, null, null, null, null, null, null, null
        );

        when(repository.findByTipoVinculo(eq("Público Externo"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(pessoa), pageable, 1));
        when(mapper.toDetalhadaResponse(pessoa)).thenReturn(response);

        Page<PessoaDetalhadaResponseDTO> resultado = service.getAllPersonsDetails(pageable);

        assertEquals(1, resultado.getContent().size());
        assertEquals("João", resultado.getContent().get(0).nome());
    }

    @Test
    void deveRetornarListaVaziaAoBuscarDetalhes() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findByTipoVinculo(eq("Público Externo"), any(Pageable.class)))
                .thenReturn(Page.empty(pageable));

        Page<PessoaDetalhadaResponseDTO> resultado = service.getAllPersonsDetails(pageable);

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

        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(enderecoPessoaRepository.findByPessoaIdPessoa(1)).thenReturn(Optional.empty());
        when(matriculaTurmaRepository.findByPessoaIdPessoa(1)).thenReturn(List.of());
        when(historicoAcademicoRepository.findByPessoaId(1)).thenReturn(List.of());

        PessoaFichaResponseDTO ficha = mock(PessoaFichaResponseDTO.class);
        when(ficha.nome()).thenReturn("Carlos");
        when(mapper.toFichaResponse(eq(pessoa),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.anyList(),
                org.mockito.ArgumentMatchers.anyList())).thenReturn(ficha);

        PessoaFichaResponseDTO resultado = service.getPersonsDetailsId(1L);

        assertEquals("Carlos", resultado.nome());
    }

    @Test
    void deveLancarExcecaoAoBuscarDetalhesInexistentes() {
        when(repository.findById(100L)).thenReturn(Optional.empty());

        PessoaNaoEncontradaException exception = assertThrows(
                PessoaNaoEncontradaException.class,
                () -> service.getPersonsDetailsId(100L)
        );

        assertTrue(exception.getMessage().contains("Pessoa não encontrada"));
    }

    @Test
    void deveChamarMapperUmaUnicaVezAoAtualizar() {
        PessoaRequestDTO dto = criarPessoaDTO();
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(repository.save(pessoa)).thenReturn(pessoa);
        when(mapper.toResponse(any())).thenReturn(mock(PessoaResponseDTO.class));

        service.updatePessoa(1L, dto);

        verify(mapper, times(1)).updateFromDto(dto, pessoa);
    }

    @Test
    void deveSalvarPessoaUmaUnicaVez() {
        PessoaRequestDTO dto = criarPessoaDTO();
        Pessoa pessoa = new Pessoa();

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(mapper.toModel(dto)).thenReturn(pessoa);
        when(repository.save(pessoa)).thenReturn(pessoa);
        when(mapper.toResponse(pessoa)).thenReturn(mock(PessoaResponseDTO.class));

        service.createPessoa(dto);

        verify(repository, times(1)).save(pessoa);
    }

    @Test
    void naoDeveSalvarQuandoPessoaNaoExistir() {
        PessoaRequestDTO dto = criarPessoaDTO();

        when(tipoVinculoRepository.findById(1)).thenReturn(Optional.of(new TipoVinculo()));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PessoaNaoEncontradaException.class, () -> service.updatePessoa(1L, dto));
        verify(repository, never()).save(any());
    }

    @Test
    void deveExecutarDeleteUmaUnicaVez() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setDataSaida(null);

        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(usuarioRepository.findByPessoaIdPessoa(1)).thenReturn(Optional.empty());
        when(repository.save(pessoa)).thenReturn(pessoa);

        service.deletePessoa(1L);

        verify(repository, times(1)).save(pessoa);
    }

    @Test
    void devePropagarErroAoDeletarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setDataSaida(null);

        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(repository.save(pessoa)).thenThrow(new RuntimeException("Erro ao deletar"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.deletePessoa(1L)
        );

        assertEquals("Erro ao deletar", exception.getMessage());
    }

    @Test
    void devePropagarErroAoListarPessoas() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findByTipoVinculo(eq("Público Externo"), any(Pageable.class)))
                .thenThrow(new RuntimeException("Falha no banco"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getAllPersons(pageable)
        );

        assertEquals("Falha no banco", exception.getMessage());
    }
}
