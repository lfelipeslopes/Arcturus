package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysPersonTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysPersonType} and its DTO {@link SysPersonTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysPersonTypeMapper extends EntityMapper<SysPersonTypeDTO, SysPersonType> {



    default SysPersonType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysPersonType sysPersonType = new SysPersonType();
        sysPersonType.setId(id);
        return sysPersonType;
    }
}
