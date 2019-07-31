package arcturus.service.mapper;

import arcturus.domain.*;
import arcturus.service.dto.SysLicenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysLicense} and its DTO {@link SysLicenseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysLicenseMapper extends EntityMapper<SysLicenseDTO, SysLicense> {



    default SysLicense fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysLicense sysLicense = new SysLicense();
        sysLicense.setId(id);
        return sysLicense;
    }
}
