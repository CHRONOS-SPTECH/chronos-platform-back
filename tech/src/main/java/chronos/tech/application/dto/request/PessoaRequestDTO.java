package chronos.tech.application.dto.request;

import java.time.LocalDate;

public record PessoaRequestDTO(String nome, String genero, LocalDate data_nascimento) {
}
