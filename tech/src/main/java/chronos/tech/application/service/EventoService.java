package chronos.tech.application.service;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.application.mapper.EventoMapper;
import chronos.tech.application.port.in.EventoUseCase;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.port.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService implements EventoUseCase {

    private final EventoRepository repository;
    private final EventoMapper mapper;

    //Método para pegar todos os eventos
    @Override
    public List<EventoResponseDTO> getAllEventos(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um evento específico
    @Override
    public EventoResponseDTO getEvento(Long id){
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o ID: " + id));
        return mapper.toResponse(evento);
    }

    //Salvar no banco de dados h2
    @Override
    public EventoResponseDTO saveEvento(EventoRequestDTO evento){
        Evento entidade = mapper.toModel(evento);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar o evento
    @Override
    public EventoResponseDTO updateEvento(Long id, EventoRequestDTO eventoAtualizado){
            Evento eventoExistente = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado com o ID: " + id));

            mapper.updateFromDto(eventoAtualizado, eventoExistente);
            return mapper.toResponse(repository.save(eventoExistente));
    }

    //Deletar um evento
    @Override
    public void deletEvento(Long id){
        repository.deleteById(id);
    }



}
