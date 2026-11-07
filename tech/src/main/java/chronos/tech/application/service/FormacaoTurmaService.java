package chronos.tech.application.service;

import chronos.tech.application.dto.turma.AvaliacaoFormacaoResponseDTO;
import chronos.tech.application.dto.turma.DecisaoFormacaoRequestDTO;
import chronos.tech.domain.model.classes.AvaliacaoFormacaoTurma;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import chronos.tech.domain.port.AvaliacaoFormacaoTurmaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TipoVinculoRepository;
import chronos.tech.infrastructure.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class FormacaoTurmaService {
    private static final String VINCULO_PROVACIONISTA = "Provacionista";
    private static final String VINCULO_MEMBRO = "Membro";
    private static final ZoneId APP_ZONE = ZoneId.of("America/Sao_Paulo");

    private final AvaliacaoFormacaoTurmaRepository avaliacaoRepository;
    private final MatriculaTurmaRepository matriculaRepository;
    private final PessoaRepository pessoaRepository;
    private final TipoVinculoRepository tipoVinculoRepository;

    @Transactional
    public void criarPendencias(Turma turma) {
        for (MatriculaTurma matricula : matriculaRepository.findByTurmaIdTurma(turma.getIdTurma())) {
            Pessoa pessoa = matricula.getPessoa();
            if (pessoa.getTipoVinculo() == null
                    || !VINCULO_PROVACIONISTA.equals(pessoa.getTipoVinculo().getNome_vinculo())) continue;

            MatriculaTurmaId id = new MatriculaTurmaId(turma.getIdTurma(), pessoa.getIdPessoa());
            if (avaliacaoRepository.findById(id).isPresent()) continue;

            AvaliacaoFormacaoTurma avaliacao = new AvaliacaoFormacaoTurma();
            avaliacao.setId(id);
            avaliacao.setTurma(turma);
            avaliacao.setPessoa(pessoa);
            avaliacaoRepository.save(avaliacao);
        }
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoFormacaoResponseDTO> listar(Long idTurma, Pageable pageable) {
        Integer turmaId = converterId(idTurma);
        return avaliacaoRepository.findByTurmaIdTurma(turmaId, limitarPagina(pageable))
                .map(this::toResponse);
    }

    @Transactional
    public AvaliacaoFormacaoResponseDTO decidir(Long idTurma, Long idPessoa, DecisaoFormacaoRequestDTO decisao) {
        MatriculaTurmaId id = new MatriculaTurmaId(converterId(idTurma), converterId(idPessoa));
        AvaliacaoFormacaoTurma avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aluno não está na lista de formandos da turma"));
        if (!"PENDENTE".equals(avaliacao.getStatusAvaliacao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A decisão deste aluno já foi registrada");
        }

        boolean aprovado = Boolean.TRUE.equals(decisao.aprovado());
        if (aprovado) aprovar(avaliacao.getPessoa());
        avaliacao.setStatusAvaliacao(aprovado ? "APROVADO" : "REPROVADO");
        avaliacao.setDataDecisao(LocalDate.now(APP_ZONE));
        avaliacao.setIdUsuarioDecisor(idUsuarioAtual());
        avaliacao.setObservacao(decisao.observacao());
        return toResponse(avaliacaoRepository.save(avaliacao));
    }

    private void aprovar(Pessoa pessoa) {
        if (pessoa.getTipoVinculo() == null
                || !VINCULO_PROVACIONISTA.equals(pessoa.getTipoVinculo().getNome_vinculo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Somente aluno Provacionista pode ser aprovado como Membro");
        }
        TipoVinculo vinculoMembro = tipoVinculoRepository.findByNome(VINCULO_MEMBRO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Vínculo Membro não cadastrado"));
        pessoa.setTipoVinculo(vinculoMembro);
        if (pessoa.getDataMembro() == null) pessoa.setDataMembro(LocalDate.now(APP_ZONE));
        pessoaRepository.save(pessoa);
    }

    private Integer idUsuarioAtual() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication == null ? null : authentication.getPrincipal();
        return principal instanceof CustomUserPrincipal user ? user.usuario().getIdUsuario() : null;
    }

    private AvaliacaoFormacaoResponseDTO toResponse(AvaliacaoFormacaoTurma avaliacao) {
        return new AvaliacaoFormacaoResponseDTO(
                avaliacao.getTurma().getIdTurma().longValue(),
                avaliacao.getPessoa().getIdPessoa().longValue(),
                avaliacao.getPessoa().getNome(),
                avaliacao.getStatusAvaliacao(),
                avaliacao.getDataDecisao(),
                avaliacao.getIdUsuarioDecisor(),
                avaliacao.getObservacao());
    }

    private Integer converterId(Long id) {
        try {
            return Math.toIntExact(id);
        } catch (ArithmeticException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID fora do intervalo permitido", e);
        }
    }

    private Pageable limitarPagina(Pageable pageable) {
        return org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), 100), pageable.getSort());
    }
}
