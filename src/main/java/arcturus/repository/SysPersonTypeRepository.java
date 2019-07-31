package arcturus.repository;

import arcturus.domain.SysPersonType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysPersonType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysPersonTypeRepository extends JpaRepository<SysPersonType, Long> {

}
