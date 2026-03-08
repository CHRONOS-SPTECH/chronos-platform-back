package chronos.tech.service;

import chronos.tech.model.classes.Evento;
import chronos.tech.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    //Método para pegar todos os eventos
    public List<Evento> getAllEventos(){
        return repository.findAll();
    }

    //Método pegar um evento específico
    public Optional<Evento> getEvento(Long id){
        return repository.findById(id);
    }

    //Salvar no banco de dados h2
    public Evento saveEvento(Evento evento){
        return repository.save(evento);
    }

    //Atualizar o evento
    public Evento updateEvento(Long id, Evento eventoAtualizado){
            Evento eventoExistente = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado com o ID: " + id));

            if (eventoAtualizado.getTitulo() != null) eventoExistente.setTitulo(eventoAtualizado.getTitulo());
            if (eventoAtualizado.getData_evento() != null) eventoExistente.setData_evento(eventoAtualizado.getData_evento());
            if (eventoAtualizado.getHora_inicio_evento() != null) eventoExistente.setHora_inicio_evento(eventoAtualizado.getHora_inicio_evento());
            if (eventoAtualizado.getHora_fim_evento() != null) eventoExistente.setHora_fim_evento(eventoAtualizado.getHora_fim_evento());
            if (eventoAtualizado.getHora_inicio_formacao() != null) eventoExistente.setHora_inicio_formacao(eventoAtualizado.getHora_inicio_formacao());
            if (eventoAtualizado.getHora_fim_formacao() != null) eventoExistente.setHora_fim_formacao(eventoAtualizado.getHora_fim_formacao());

            if (eventoAtualizado.getId_categoria() != null) eventoExistente.setId_categoria(eventoAtualizado.getId_categoria());
            if (eventoAtualizado.getId_secretaria() != null) eventoExistente.setId_secretaria(eventoAtualizado.getId_secretaria());
            if (eventoAtualizado.getId_turma() != null) eventoExistente.setId_turma(eventoAtualizado.getId_turma());
            if (eventoAtualizado.getId_tema() != null) eventoExistente.setId_tema(eventoAtualizado.getId_tema());

            return repository.save(eventoExistente);
    }

    //Deletar um evento
    public void deletEvento(Long id){
        repository.deleteById(id);
    }

}
