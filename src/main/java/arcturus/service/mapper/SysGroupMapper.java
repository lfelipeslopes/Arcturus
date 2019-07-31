package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysGroup} and its DTO {@link SysGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysIpGroupMapper.class, SysAccessMapper.class})
public interface SysGroupMapper extends EntityMapper<SysGroupDTO, SysGroup> {


    @Mapping(target = "removeIpGroupId", ignore = true)
    @Mapping(target = "removeAccessId", ignore = true)
    @Mapping(target = "enterpriseIds", ignore = true)
    @Mapping(target = "removeEnterpriseId", ignore = true)
    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "removeUserId", ignore = true)
    SysGroup toEntity(SysGroupDTO sysGroupDTO);

    default SysGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysGroup sysGroup = new SysGroup();
        sysGroup.setId(id);
        return sysGroup;
    }
}
