package chronos.tech.application.usecase;

import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.ports.input.JpaPessoaEntity;
import chronos.tech.domain.ports.output.JpaPessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaUseCase {
    private JpaPessoaRepository jpaRepository;
    public Optional<JpaPessoaEntity> buscarPessoaPorId(Long id) {
        
        return jpaRepository.findById(id);
        
    }
    
}
