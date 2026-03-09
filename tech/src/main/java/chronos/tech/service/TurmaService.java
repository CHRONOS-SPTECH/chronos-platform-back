package chronos.tech.service;

import chronos.tech.model.classes.Turma;
import chronos.tech.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    //Método para pegar todas as turmas
    public List<Turma> getAllTurmas() {

        var allTurmas = repository.findAll();
        return allTurmas;

    }

    //Método pegar uma turma específica
    public Optional<Turma> getTurma(Long id){

        return repository.findById(id);

    }

    //Salvar no banco de dados h2
    public Turma saveTurma(Turma turma){
        return repository.save(turma);
    }

    //Atualizar a turma
    public Turma updateTurma(Long id, Turma turmaAtualizado){
        Turma turmaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));

        if (turmaAtualizado.getNome_turma() != null) turmaExistente.setNome_turma(turmaAtualizado.getNome_turma());
        if (turmaAtualizado.getData_inicio() != null) turmaExistente.setData_inicio(turmaAtualizado.getData_inicio());
        if (turmaAtualizado.getData_encerramento() != null) turmaExistente.setData_encerramento(turmaAtualizado.getData_encerramento());
        if (turmaAtualizado.getStatus_encerrado() != null) turmaExistente.setStatus_encerrado(turmaAtualizado.getStatus_encerrado());

        return repository.save(turmaExistente);
    }

    //Deletar uma turma
    public void deleteTurma(Long id){
        repository.deleteById(id);
    }

}
