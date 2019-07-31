package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysAccess} and its DTO {@link SysAccessDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysModuleMapper.class})
public interface SysAccessMapper extends EntityMapper<SysAccessDTO, SysAccess> {

    @Mapping(source = "sysModule.id", target = "sysModuleId")
    SysAccessDTO toDto(SysAccess sysAccess);

    @Mapping(source = "sysModuleId", target = "sysModule")
    @Mapping(target = "groupIds", ignore = true)
    @Mapping(target = "removeGroupId", ignore = true)
    SysAccess toEntity(SysAccessDTO sysAccessDTO);

    default SysAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysAccess sysAccess = new SysAccess();
        sysAccess.setId(id);
        return sysAccess;
    }
}
