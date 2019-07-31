package arcturus.repository;

import arcturus.domain.SysGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SysGroup entity.
 */
@Repository
public interface SysGroupRepository extends JpaRepository<SysGroup, Long> {

    @Query(value = "select distinct sysGroup from SysGroup sysGroup left join fetch sysGroup.ipGroupIds left join fetch sysGroup.accessIds",
        countQuery = "select count(distinct sysGroup) from SysGroup sysGroup")
    Page<SysGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct sysGroup from SysGroup sysGroup left join fetch sysGroup.ipGroupIds left join fetch sysGroup.accessIds")
    List<SysGroup> findAllWithEagerRelationships();

    @Query("select sysGroup from SysGroup sysGroup left join fetch sysGroup.ipGroupIds left join fetch sysGroup.accessIds where sysGroup.id =:id")
    Optional<SysGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
