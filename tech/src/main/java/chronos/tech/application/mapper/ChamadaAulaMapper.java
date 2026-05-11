package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.Pessoa;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ChamadaAulaMapper {
    @Mapping(target = "id.idAula", source = "id_aula")
    @Mapping(target = "id.idPessoa", source = "id_pessoa")
    @Mapping(target = "aula", source = "id_aula")
    @Mapping(target = "pessoa", source = "id_pessoa")
    ChamadaAula toModel(ChamadaAulaRequestDTO dto);

    @Mapping(target = "id_aula", source = "aula.idAula")
    @Mapping(target = "id_pessoa", source = "pessoa.idPessoa")
    ChamadaAulaResponseDTO toResponse(ChamadaAula chamadaAula);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aula", source = "id_aula")
    @Mapping(target = "pessoa", source = "id_pessoa")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ChamadaAulaRequestDTO dto, @MappingTarget ChamadaAula chamadaAula);

    default Aula mapAula(Long id_aula) {
        if (id_aula == null) return null;
        Aula aula = new Aula();
        aula.setIdAula(Math.toIntExact(id_aula));
        return aula;
    }

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }
}
