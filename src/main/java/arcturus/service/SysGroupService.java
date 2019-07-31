package arcturus.service;

import arcturus.service.dto.SysGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysGroup}.
 */
public interface SysGroupService {

    /**
     * Save a sysGroup.
     *
     * @param sysGroupDTO the entity to save.
     * @return the persisted entity.
     */
    SysGroupDTO save(SysGroupDTO sysGroupDTO);

    /**
     * Get all the sysGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysGroupDTO> findAll(Pageable pageable);

    /**
     * Get all the sysGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SysGroupDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" sysGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysGroupDTO> findOne(Long id);

    /**
     * Delete the "id" sysGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
