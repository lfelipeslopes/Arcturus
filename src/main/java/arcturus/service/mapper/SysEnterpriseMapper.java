package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysEnterpriseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysEnterprise} and its DTO {@link SysEnterpriseDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysLicenseMapper.class, SysGroupMapper.class})
public interface SysEnterpriseMapper extends EntityMapper<SysEnterpriseDTO, SysEnterprise> {

    @Mapping(source = "sysLicense.id", target = "sysLicenseId")
    SysEnterpriseDTO toDto(SysEnterprise sysEnterprise);

    @Mapping(source = "sysLicenseId", target = "sysLicense")
    @Mapping(target = "removeGroupId", ignore = true)
    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "removeUserId", ignore = true)
    SysEnterprise toEntity(SysEnterpriseDTO sysEnterpriseDTO);

    default SysEnterprise fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysEnterprise sysEnterprise = new SysEnterprise();
        sysEnterprise.setId(id);
        return sysEnterprise;
    }
}
