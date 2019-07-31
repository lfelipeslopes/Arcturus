package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysUser} and its DTO {@link SysUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysPersonMapper.class, SysGroupMapper.class, SysEnterpriseMapper.class})
public interface SysUserMapper extends EntityMapper<SysUserDTO, SysUser> {

    @Mapping(source = "sysPerson.id", target = "sysPersonId")
    SysUserDTO toDto(SysUser sysUser);

    @Mapping(source = "sysPersonId", target = "sysPerson")
    @Mapping(target = "removeGroupId", ignore = true)
    @Mapping(target = "removeEnterpriseId", ignore = true)
    SysUser toEntity(SysUserDTO sysUserDTO);

    default SysUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        return sysUser;
    }
}
