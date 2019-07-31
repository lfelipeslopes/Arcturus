package arcturus.repository;

import arcturus.domain.SysAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysAccessRepository extends JpaRepository<SysAccess, Long> {

}
