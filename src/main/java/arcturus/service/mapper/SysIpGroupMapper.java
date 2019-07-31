package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysIpGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysIpGroup} and its DTO {@link SysIpGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysIpGroupItemMapper.class})
public interface SysIpGroupMapper extends EntityMapper<SysIpGroupDTO, SysIpGroup> {

    @Mapping(source = "sysIpGroupItem.id", target = "sysIpGroupItemId")
    SysIpGroupDTO toDto(SysIpGroup sysIpGroup);

    @Mapping(target = "groupIds", ignore = true)
    @Mapping(target = "removeGroupId", ignore = true)
    @Mapping(source = "sysIpGroupItemId", target = "sysIpGroupItem")
    SysIpGroup toEntity(SysIpGroupDTO sysIpGroupDTO);

    default SysIpGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysIpGroup sysIpGroup = new SysIpGroup();
        sysIpGroup.setId(id);
        return sysIpGroup;
    }
}
