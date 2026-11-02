package chronos.tech.application.service;

import chronos.tech.application.dto.pessoa.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaBiometriaRequestDTO;
import chronos.tech.application.dto.pessoa.EnderecoPessoaCadastroRequestDTO;
import chronos.tech.application.exception.DadosPessoaInvalidosException;
import chronos.tech.application.dto.pessoa.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaFichaResponseDTO;
import chronos.tech.application.dto.pessoa.EnderecoPessoaResponseDTO;
import chronos.tech.application.dto.pessoa.HistoricoAcademicoDetalheResponseDTO;
import chronos.tech.application.dto.pessoa.TurmaPessoaDetalheResponseDTO;
import chronos.tech.application.mapper.EnderecoPessoaMapper;
import chronos.tech.application.dto.pessoa.PessoaResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoListaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaPublicoExternoListaResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.port.in.PessoaUseCase;
import chronos.tech.application.port.out.CryptoPort;
import chronos.tech.application.port.out.FileStoragePort;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.EnderecoPessoa;
import chronos.tech.domain.model.classes.HistoricoAcademico;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.application.exception.PessoaNaoEncontradaException;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.TipoVinculoRepository;
import chronos.tech.domain.port.UsuarioRepository;
import chronos.tech.domain.port.EnderecoPessoaRepository;
import chronos.tech.domain.port.HistoricoAcademicoRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.infrastructure.utils.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService implements PessoaUseCase {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final PessoaRepository repository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final TipoVinculoRepository tipoVinculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoPessoaRepository enderecoPessoaRepository;
    private final HistoricoAcademicoRepository historicoAcademicoRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;
    private final PessoaMapper mapper;
    private final EnderecoPessoaMapper enderecoPessoaMapper;
    private final CryptoPort cryptoPort;
    private final FileStoragePort fileStoragePort;

    @Override
    @Transactional(readOnly = true)
    public Page<PessoaDetalhadaResponseDTO> getAllPersonsDetails(Pageable pageable) {
        return repository.findByTipoVinculo("Público Externo", normalize(pageable))
                .map(mapper::toDetalhadaResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaFichaResponseDTO getPersonsDetailsId(Long id) {
        Pessoa pessoa = buscarPessoa(id);
        EnderecoPessoaResponseDTO endereco = enderecoPessoaRepository
                .findByPessoaIdPessoa(pessoa.getIdPessoa())
                .map(enderecoPessoaMapper::toResponse)
                .orElse(null);

        var turmas = matriculaTurmaRepository.findByPessoaIdPessoa(pessoa.getIdPessoa()).stream()
                .map(this::toTurmaDetalhe)
                .toList();
        var historico = historicoAcademicoRepository.findByPessoaId(pessoa.getIdPessoa()).stream()
                .map(this::toHistoricoDetalhe)
                .toList();

        return mapper.toFichaResponse(pessoa, endereco, turmas, historico);
    }

    @Override
    @Transactional
    public PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto) {
        validarTipoVinculo(requestPessoaDto.tipo_vinculo_id());
        Pessoa pessoa = mapper.toModel(requestPessoaDto);
        Pessoa pessoaSave = repository.save(pessoa);
        salvarEnderecoSeInformado(pessoaSave, requestPessoaDto.endereco());
        return mapper.toResponse(pessoaSave);

    }


    @Override
    @Transactional
    public PessoaResponseDTO createPessoaComBiometria(PessoaRegistroRequestDTO requestDto) {
        validarTipoVinculo(requestDto.tipo_vinculo_id());
        Pessoa pessoa = mapper.toModel(requestDto);
        String imagemBase64 = requestDto.imagemPerfilBase64();
        if (imagemBase64 != null && !imagemBase64.isBlank()) {
            pessoa.setUrlFotoPerfil(uploadImagemPerfil(imagemBase64));
        }
        if (requestDto.vetorBiometrico() != null && !requestDto.vetorBiometrico().isEmpty()) {
            pessoa.setBiometriaFacial(criptografarVetor(requestDto.vetorBiometrico()));
        }

        Pessoa pessoaSalva = repository.save(pessoa);
        salvarEnderecoSeInformado(pessoaSalva, requestDto.endereco());
        return mapper.toResponse(pessoaSalva);
    }

    @Override
    public PessoaResponseDTO pegarPorId(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        return mapper.toResponse(pessoa);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PessoaPublicoExternoListaResponseDTO> getAllPersons(Pageable pageable) {
        return repository.findByTipoVinculo("Público Externo", normalize(pageable))
                .map(this::toPublicoExternoLista);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlunoListaResponseDTO> getAllAlunos(Pageable pageable) {
        return matriculaTurmaRepository.findAlunos(normalize(pageable)).map(this::toAlunoLista);
    }

    private PessoaPublicoExternoListaResponseDTO toPublicoExternoLista(Pessoa pessoa) {
        String cpf = pessoa.getCpf();
        String digitosCpf = cpf == null ? "" : cpf.replaceAll("\\D", "");
        String cpfMascarado = digitosCpf.length() < 3
                ? null
                : "***.***.***-" + digitosCpf.substring(digitosCpf.length() - 3);
        var vinculo = pessoa.getTipoVinculo();
        return new PessoaPublicoExternoListaResponseDTO(
                pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(),
                cpfMascarado,
                vinculo == null ? null : vinculo.getIdTipoVinculo(),
                vinculo == null ? null : vinculo.getNome_vinculo());
    }

    private AlunoListaResponseDTO toAlunoLista(Pessoa pessoa) {
        List<MatriculaTurma> matriculasAtuais = matriculaTurmaRepository
                .findByPessoaIdPessoa(pessoa.getIdPessoa()).stream()
                .filter(matricula -> matricula.getTurma() != null
                        && matricula.getTurma().getStatusTurma() == StatusTurma.EM_ANDAMENTO)
                .toList();
        List<AlunoListaResponseDTO.TurmaAtualDTO> turmasAtuais = matriculasAtuais.stream()
                .map(matricula -> new AlunoListaResponseDTO.TurmaAtualDTO(
                        matricula.getTurma().getIdTurma(), matricula.getTurma().getNomeTurma()))
                .toList();

        long totalChamadas = 0;
        long presencas = 0;
        for (MatriculaTurma matricula : matriculasAtuais) {
            List<ChamadaAula> chamadas = chamadaAulaRepository.findByPessoaIdPessoaAndAulaTurmaIdTurma(
                    pessoa.getIdPessoa(), matricula.getTurma().getIdTurma());
            totalChamadas += chamadas.size();
            presencas += chamadas.stream().filter(chamada -> Boolean.TRUE.equals(chamada.getCompareceu())).count();
        }
        Double percentualPresenca = totalChamadas == 0 ? 0.0 : presencas * 100.0 / totalChamadas;
        var vinculo = pessoa.getTipoVinculo();
        return new AlunoListaResponseDTO(
                pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(),
                percentualPresenca, turmasAtuais, pessoa.getBolsista(), pessoa.getUrlFotoPerfil(),
                vinculo == null ? null : vinculo.getNome_vinculo(),
                vinculo == null ? null : vinculo.getIdTipoVinculo(),
                pessoa.getDataNascimento(), pessoa.getDataIngresso(), pessoa.getDataMembro(), pessoa.getDataSaida());
    }

    @Override
    @Transactional
    public PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado) {
        validarTipoVinculo(pessoaAtualizado.tipo_vinculo_id());
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        mapper.updateFromDto(pessoaAtualizado, pessoaExistente);
        Pessoa pessoaAtualizada = repository.save(pessoaExistente);
        salvarEnderecoSeInformado(pessoaAtualizada, pessoaAtualizado.endereco());
        return mapper.toResponse(pessoaAtualizada);
    }

    @Override
    public void deletePessoa(Long id) {
        inativarPessoa(id);
    }

    @Override
    @Transactional
    public void inativarPessoa(Long id) {
        Pessoa pessoa = buscarPessoa(id);
        if (pessoa.getDataSaida() == null) {
            pessoa.setDataSaida(java.time.LocalDate.now());
            repository.save(pessoa);
            usuarioRepository.findByPessoaIdPessoa(pessoa.getIdPessoa()).ifPresent(usuario -> {
                usuario.setStatusAtivo(Boolean.FALSE);
                usuarioRepository.save(usuario);
            });
        }
    }

    @Override
    @Transactional
    public void reativarPessoa(Long id) {
        Pessoa pessoa = buscarPessoa(id);
        if (pessoa.getDataSaida() != null) {
            pessoa.setDataSaida(null);
            repository.save(pessoa);
        }
    }

    @Override
    @Transactional
    public PessoaResponseDTO atualizarBiometria(Long id, PessoaBiometriaRequestDTO request) {
        Pessoa pessoa = buscarPessoa(id);
        pessoa.setBiometriaFacial(criptografarVetor(request.vetorBiometrico()));
        if (request.imagemPerfilBase64() != null && !request.imagemPerfilBase64().isBlank()) {
            pessoa.setUrlFotoPerfil(uploadImagemPerfil(request.imagemPerfilBase64()));
        }
        return mapper.toResponse(repository.save(pessoa));
    }

    @Override
    @Transactional
    public void removerBiometria(Long id) {
        Pessoa pessoa = buscarPessoa(id);
        pessoa.setBiometriaFacial(null);
        repository.save(pessoa);
    }

    private Pessoa buscarPessoa(Long id) {
        return repository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    private void salvarEnderecoSeInformado(Pessoa pessoa, EnderecoPessoaCadastroRequestDTO request) {
        if (request == null) return;

        EnderecoPessoa endereco = enderecoPessoaRepository.findByPessoaIdPessoa(pessoa.getIdPessoa())
                .orElseGet(() -> {
                    EnderecoPessoa novo = enderecoPessoaMapper.toModel(request);
                    novo.setPessoa(pessoa);
                    return novo;
                });
        enderecoPessoaMapper.updateFromCadastroDto(request, endereco);
        endereco.setPessoa(pessoa);
        enderecoPessoaRepository.save(endereco);
    }

    private TurmaPessoaDetalheResponseDTO toTurmaDetalhe(MatriculaTurma matricula) {
        var turma = matricula.getTurma();
        var chamadas = chamadaAulaRepository.findByPessoaIdPessoaAndAulaTurmaIdTurma(
                matricula.getPessoa().getIdPessoa(), turma.getIdTurma());
        long presencas = chamadas.stream()
                .filter(chamada -> Boolean.TRUE.equals(chamada.getCompareceu()))
                .count();
        double percentual = chamadas.isEmpty() ? 0.0 : presencas * 100.0 / chamadas.size();
        return new TurmaPessoaDetalheResponseDTO(
                turma.getIdTurma(), turma.getNomeTurma(), turma.getStatusTurma(),
                matricula.getDataMatricula(), chamadas.size(), presencas, percentual);
    }

    private HistoricoAcademicoDetalheResponseDTO toHistoricoDetalhe(HistoricoAcademico historico) {
        var materia = historico.getIdMateria();
        var nivel = materia == null ? null : materia.getIdNivel();
        return new HistoricoAcademicoDetalheResponseDTO(
                historico.getIdHistorico(),
                materia == null ? null : materia.getIdMateria(),
                materia == null ? null : materia.getNome(),
                nivel == null ? null : nivel.getIdNivel(),
                nivel == null ? null : nivel.getNomeNivel(),
                nivel == null ? null : nivel.getOrdemHierarquia(),
                historico.getStatusMateria(),
                historico.getDataStatus());
    }

    private void validarTipoVinculo(Integer id) {
        tipoVinculoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tipo de vínculo não encontrado: " + id));
    }

    private Pageable normalize(Pageable pageable) {
        int size = Math.min(pageable.getPageSize(), 100);
        Sort sort = pageable.getSort().and(Sort.by(Sort.Order.asc("idPessoa")));
        return PageRequest.of(pageable.getPageNumber(), size, sort);
    }

    private String criptografarVetor(java.util.List<Double> vetor) {
        try {
            return cryptoPort.encrypt(JSON_MAPPER.writeValueAsString(vetor));
        } catch (JsonProcessingException e) {
            throw new DadosPessoaInvalidosException("Não foi possível processar o vetor biométrico", e);
        }
    }

    private String uploadImagemPerfil(String imagemBase64) {
        try {
            String mimeType = "image/jpeg";
            String base64Limpo = imagemBase64;
            if (imagemBase64.startsWith("data:")) {
                int separator = imagemBase64.indexOf(",");
                if (separator < 0) throw new IllegalArgumentException("Data URL inválida");
                String metadata = imagemBase64.substring(5, separator);
                int semicolon = metadata.indexOf(';');
                mimeType = semicolon < 0 ? metadata : metadata.substring(0, semicolon);
                base64Limpo = imagemBase64.substring(separator + 1);
            }
            if (!java.util.Set.of("image/jpeg", "image/png").contains(mimeType)) {
                throw new IllegalArgumentException("Formato de imagem não suportado");
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Limpo);
            String extension = switch (mimeType) {
                case "image/png" -> "png";
                default -> "jpg";
            };
            MultipartFile file = new CustomMultipartFile(imageBytes, "foto-perfil." + extension, mimeType);
            if (!fileStoragePort.validateFile(file)) {
                throw new IllegalArgumentException("Imagem de perfil inválida");
            }
            return fileStoragePort.uploadFile(file, "perfil-imagens");
        } catch (IllegalArgumentException e) {
            throw new DadosPessoaInvalidosException("Imagem de perfil inválida: " + e.getMessage(), e);
        }
    }
}
