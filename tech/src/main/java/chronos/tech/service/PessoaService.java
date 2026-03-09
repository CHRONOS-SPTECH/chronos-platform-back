package chronos.tech.service;

import chronos.tech.model.classes.Pessoa;
import chronos.tech.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    //Método para pegar todas as pessoas
    public List<Pessoa> getAllPersons() {

        var allPersons = repository.findAll();
        return allPersons;

    }

    //Método pegar uma pessoa específico
    public Optional<Pessoa> getPessoa(Long id){

        return repository.findById(id);

    }

    //Salvar no banco de dados h2
    public Pessoa savePessoa(Pessoa pessoa){
        return repository.save(pessoa);
    }

    //Atualizar a pessoa
    public Pessoa updatePessoa(Long id, Pessoa pessoaAtualizado){
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));

        if (pessoaAtualizado.getNome() != null) pessoaExistente.setNome(pessoaAtualizado.getNome());
        if (pessoaAtualizado.getGenero() != null) pessoaExistente.setGenero(pessoaAtualizado.getGenero());
        if (pessoaAtualizado.getTipoVinculo() != null) pessoaExistente.setTipoVinculo(pessoaAtualizado.getTipoVinculo());
        if (pessoaAtualizado.getData_nascimento() != null) pessoaExistente.setData_nascimento(pessoaAtualizado.getData_nascimento());
        if (pessoaAtualizado.getData_ingresso() != null) pessoaExistente.setData_ingresso(pessoaAtualizado.getData_ingresso());
        if (pessoaAtualizado.getData_membro() != null) pessoaExistente.setData_membro(pessoaAtualizado.getData_membro());
        if (pessoaAtualizado.getData_saida() != null) pessoaExistente.setData_saida(pessoaAtualizado.getData_saida());

        return repository.save(pessoaExistente);
    }

    //Deletar uma pessoa
    public void deletePessoa(Long id){
        repository.deleteById(id);
    }

}
