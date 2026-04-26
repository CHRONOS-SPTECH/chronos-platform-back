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
    @Mapping(target = "idHistorico", ignore = true)
    @Mapping(target = "idPessoa", source = "id_pessoa")
    @Mapping(target = "idMateria", source = "id_materia")
    @Mapping(target = "statusMateria", source = "status_materia")
    @Mapping(target = "dataStatus", source = "data_status")
    HistoricoAcademico toModel(HistoricoAcademicoRequestDTO dto);

    @Mapping(target = "id_historico", source = "idHistorico")
    @Mapping(target = "id_pessoa", source = "idPessoa.idPessoa")
    @Mapping(target = "id_materia", source = "idMateria.idMateria")
    @Mapping(target = "status_materia", source = "statusMateria")
    @Mapping(target = "data_status", source = "dataStatus")
    HistoricoAcademicoResponseDTO toResponse(HistoricoAcademico historico);

    @Mapping(target = "idHistorico", ignore = true)
    @Mapping(target = "idPessoa", source = "id_pessoa")
    @Mapping(target = "idMateria", source = "id_materia")
    @Mapping(target = "statusMateria", source = "status_materia")
    @Mapping(target = "dataStatus", source = "data_status")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(HistoricoAcademicoRequestDTO dto, @MappingTarget HistoricoAcademico historico);

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }

    default Materia mapMateria(Long id_materia) {
        if (id_materia == null) return null;
        Materia materia = new Materia();
        materia.setIdMateria(Math.toIntExact(id_materia));
        return materia;
    }
}
