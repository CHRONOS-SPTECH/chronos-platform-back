package chronos.tech.dto.mapper;

import chronos.tech.dto.request.CreatePessoaRequestDto;
import chronos.tech.dto.response.PessoaResponseDto;
import chronos.tech.model.classes.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toModel(CreatePessoaRequestDto dto);

    PessoaResponseDto toResponse(Pessoa pessoa);

}
