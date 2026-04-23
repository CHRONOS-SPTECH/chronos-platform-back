package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.HistoricoAcademicoRequestDTO;
import chronos.tech.application.dto.response.HistoricoAcademicoResponseDTO;
import chronos.tech.domain.model.classes.HistoricoAcademico;
import chronos.tech.domain.model.classes.Materia;
import chronos.tech.domain.model.classes.Pessoa;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HistoricoAcademicoMapper {
    @Mapping(target = "id_historico", ignore = true)
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_materia", source = "id_materia")
    HistoricoAcademico toModel(HistoricoAcademicoRequestDTO dto);

    @Mapping(target = "id_pessoa", source = "id_pessoa.id_pessoa")
    @Mapping(target = "id_materia", source = "id_materia.id_materia")
    HistoricoAcademicoResponseDTO toResponse(HistoricoAcademico historico);

    @Mapping(target = "id_historico", ignore = true)
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_materia", source = "id_materia")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(HistoricoAcademicoRequestDTO dto, @MappingTarget HistoricoAcademico historico);

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(id_pessoa);
        return pessoa;
    }

    default Materia mapMateria(Long id_materia) {
        if (id_materia == null) return null;
        Materia materia = new Materia();
        materia.setId_materia(id_materia);
        return materia;
    }
}
