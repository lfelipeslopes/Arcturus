package arcturus.repository;

import arcturus.domain.SysIpGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysIpGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysIpGroupRepository extends JpaRepository<SysIpGroup, Long> {

}
