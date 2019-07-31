package arcturus.repository;

import arcturus.domain.SysLicense;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysLicense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysLicenseRepository extends JpaRepository<SysLicense, Long> {

}
