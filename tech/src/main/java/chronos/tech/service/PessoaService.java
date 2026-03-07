package chronos.tech.service;

import chronos.tech.model.classes.Pessoa;
import chronos.tech.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> getAllPersons() {

        var allPersons = repository.findAll();
        return allPersons;

    }

}
