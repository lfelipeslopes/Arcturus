package arcturus.repository;

import arcturus.domain.SysIpGroupItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysIpGroupItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysIpGroupItemRepository extends JpaRepository<SysIpGroupItem, Long> {

}
