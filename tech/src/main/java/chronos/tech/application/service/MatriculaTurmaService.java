package chronos.tech.application.service;

import chronos.tech.application.dto.turma.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.turma.MatriculaTurmaResponseDTO;
import chronos.tech.application.mapper.MatriculaTurmaMapper;
import chronos.tech.application.port.in.MatriculaTurmaUseCase;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TurmaRepository;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaTurmaService implements MatriculaTurmaUseCase {
    private final MatriculaTurmaRepository repository;
    private final MatriculaTurmaMapper mapper;
    private final PessoaRepository pessoaRepository;
    private final TurmaRepository turmaRepository;

    @Override
    public List<MatriculaTurmaResponseDTO> getAllMatriculas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public MatriculaTurmaResponseDTO getMatricula(Integer idTurma, Integer idPessoa) {
        MatriculaTurma matricula = repository.findById(id(idTurma, idPessoa))
                .orElseThrow(() -> naoEncontrada(idTurma, idPessoa));
        return mapper.toResponse(matricula);
    }

    @Override
    @Transactional
    public MatriculaTurmaResponseDTO saveMatricula(MatriculaTurmaRequestDTO dto) {
        Integer turmaId = id(dto.id_turma());
        Integer pessoaId = id(dto.id_pessoa());
        Pessoa pessoa = pessoaRepository.findById(pessoaId.longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada: " + pessoaId));
        turmaRepository.findById(turmaId.longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + turmaId));
        if (pessoa.getTipoVinculo() == null || !java.util.Set.of(
                "Provacionista", "Membro", "Membro Força Viva"
        ).contains(pessoa.getTipoVinculo().getNome_vinculo())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Somente Provacionista, Membro ou Membro Força Viva pode ser matriculado em turma");
        }
        if (repository.findById(id(turmaId, pessoaId)).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A pessoa já está matriculada nesta turma");
        }
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    @Override
    @Transactional
    public MatriculaTurmaResponseDTO updateMatricula(Integer idTurma, Integer idPessoa, MatriculaTurmaRequestDTO dto) {
        MatriculaTurma existente = repository.findById(id(idTurma, idPessoa))
                .orElseThrow(() -> naoEncontrada(idTurma, idPessoa));
        if (!id(dto.id_turma()).equals(idTurma) || !id(dto.id_pessoa()).equals(idPessoa)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Os IDs do corpo devem corresponder à matrícula informada na rota");
        }
        mapper.updateFromDto(dto, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    @Transactional
    public void deleteMatricula(Integer idTurma, Integer idPessoa) {
        MatriculaTurmaId id = id(idTurma, idPessoa);
        if (repository.findById(id).isEmpty()) throw naoEncontrada(idTurma, idPessoa);
        repository.deleteById(id);
    }

    private MatriculaTurmaId id(Integer turmaId, Integer pessoaId) {
        return new MatriculaTurmaId(turmaId, pessoaId);
    }

    private Integer id(Long id) {
        try {
            return Math.toIntExact(id);
        } catch (ArithmeticException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID fora do intervalo permitido", e);
        }
    }

    private ResponseStatusException naoEncontrada(Integer turmaId, Integer pessoaId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Matrícula não encontrada para turma " + turmaId + " e pessoa " + pessoaId);
    }
}
