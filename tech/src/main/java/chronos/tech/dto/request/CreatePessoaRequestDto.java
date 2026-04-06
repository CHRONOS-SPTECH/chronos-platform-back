package chronos.tech.dto.request;

import java.time.LocalDate;

public record CreatePessoaRequestDto(String nome, String genero, LocalDate data_nascimento) {
}
