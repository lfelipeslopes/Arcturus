package arcturus.repository;

import arcturus.domain.SysPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysPersonRepository extends JpaRepository<SysPerson, Long> {

}
