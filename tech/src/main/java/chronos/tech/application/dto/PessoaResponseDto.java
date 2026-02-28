package chronos.tech.application.dto;

import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.enums.TipoVinculo;
import java.time.LocalDate;

public record PessoaResponseDto(
        String nome,
        String genero,
        TipoVinculo tipoVinculo,
        LocalDate data_nascimento,
        LocalDate data_ingresso,
        LocalDate data_membro,
        LocalDate data_saida
) {

    public static PessoaResponseDto fromDomain(Pessoa pessoa){

        return new PessoaResponseDto(pessoa.getNome(), pessoa.getGenero(), pessoa.getTipoVinculo(),
                pessoa.getData_nascimento(), pessoa.getData_ingresso(),
                pessoa.getData_membro(), pessoa.getData_saida());
    }

}
