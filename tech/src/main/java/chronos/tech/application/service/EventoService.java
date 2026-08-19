package chronos.tech.application.service;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.application.mapper.EventoMapper;
import chronos.tech.application.port.in.EventoUseCase;
import chronos.tech.domain.model.classes.CategoriaAtividade;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.Secretaria;
import chronos.tech.domain.port.CategoriaAtividadeRepository;
import chronos.tech.domain.port.EventoRepository;
import chronos.tech.domain.port.SecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventoService implements EventoUseCase {

    private final EventoRepository repository;
    private final CategoriaAtividadeRepository categoriaRepository;
    private final SecretariaRepository secretariaRepository;
    private final EventoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventoResponseDTO> getAllEventos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventoResponseDTO getEvento(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento não encontrado com o ID: " + id
                        ));

        return mapper.toResponse(evento);
    }

    @Override
    public EventoResponseDTO saveEvento(EventoRequestDTO dto) {
        CategoriaAtividade categoria =
                categoriaRepository.findById(dto.id_categoria())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Categoria não encontrada com o ID: "
                                                + dto.id_categoria()
                                ));

        Secretaria secretaria =
                secretariaRepository.findById(dto.id_secretaria())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Secretaria não encontrada com o ID: "
                                                + dto.id_secretaria()
                                ));

        Evento evento = mapper.toModel(dto);

        evento.setIdCategoria(categoria);
        evento.setIdSecretaria(secretaria);

        Evento salvo = repository.save(evento);

        return mapper.toResponse(salvo);
    }

    @Override
    public EventoResponseDTO updateEvento(
            Long id,
            EventoRequestDTO dto
    ) {
        Evento eventoExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento não encontrado com o ID: " + id
                        ));

        CategoriaAtividade categoria =
                categoriaRepository.findById(dto.id_categoria())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Categoria não encontrada com o ID: "
                                                + dto.id_categoria()
                                ));

        Secretaria secretaria =
                secretariaRepository.findById(dto.id_secretaria())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Secretaria não encontrada com o ID: "
                                                + dto.id_secretaria()
                                ));

        mapper.updateFromDto(dto, eventoExistente);

        eventoExistente.setIdCategoria(categoria);
        eventoExistente.setIdSecretaria(secretaria);

        Evento atualizado = repository.save(eventoExistente);

        return mapper.toResponse(atualizado);
    }

    @Override
    public void deletEvento(Long id) {
        repository.deleteById(id);
    }
}