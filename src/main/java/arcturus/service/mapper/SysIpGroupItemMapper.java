package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysIpGroupItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysIpGroupItem} and its DTO {@link SysIpGroupItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysIpGroupItemMapper extends EntityMapper<SysIpGroupItemDTO, SysIpGroupItem> {


    @Mapping(target = "sysIpGroups", ignore = true)
    @Mapping(target = "removeSysIpGroup", ignore = true)
    SysIpGroupItem toEntity(SysIpGroupItemDTO sysIpGroupItemDTO);

    default SysIpGroupItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysIpGroupItem sysIpGroupItem = new SysIpGroupItem();
        sysIpGroupItem.setId(id);
        return sysIpGroupItem;
    }
}
