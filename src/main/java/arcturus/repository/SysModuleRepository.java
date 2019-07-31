package arcturus.repository;

import arcturus.domain.SysModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysModuleRepository extends JpaRepository<SysModule, Long> {

}
