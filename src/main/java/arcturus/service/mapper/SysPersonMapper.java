package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysPerson} and its DTO {@link SysPersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysPersonTypeMapper.class})
public interface SysPersonMapper extends EntityMapper<SysPersonDTO, SysPerson> {

    @Mapping(source = "sysPersonType.id", target = "sysPersonTypeId")
    SysPersonDTO toDto(SysPerson sysPerson);

    @Mapping(source = "sysPersonTypeId", target = "sysPersonType")
    SysPerson toEntity(SysPersonDTO sysPersonDTO);

    default SysPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysPerson sysPerson = new SysPerson();
        sysPerson.setId(id);
        return sysPerson;
    }
}
