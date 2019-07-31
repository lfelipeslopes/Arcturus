package arcturus.repository;

import arcturus.domain.SysEnterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SysEnterprise entity.
 */
@Repository
public interface SysEnterpriseRepository extends JpaRepository<SysEnterprise, Long> {

    @Query(value = "select distinct sysEnterprise from SysEnterprise sysEnterprise left join fetch sysEnterprise.groupIds",
        countQuery = "select count(distinct sysEnterprise) from SysEnterprise sysEnterprise")
    Page<SysEnterprise> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct sysEnterprise from SysEnterprise sysEnterprise left join fetch sysEnterprise.groupIds")
    List<SysEnterprise> findAllWithEagerRelationships();

    @Query("select sysEnterprise from SysEnterprise sysEnterprise left join fetch sysEnterprise.groupIds where sysEnterprise.id =:id")
    Optional<SysEnterprise> findOneWithEagerRelationships(@Param("id") Long id);

}
