package arcturus.service;

import arcturus.service.dto.SysIpGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysIpGroup}.
 */
public interface SysIpGroupService {

    /**
     * Save a sysIpGroup.
     *
     * @param sysIpGroupDTO the entity to save.
     * @return the persisted entity.
     */
    SysIpGroupDTO save(SysIpGroupDTO sysIpGroupDTO);

    /**
     * Get all the sysIpGroups.
     *
     * @return the list of entities.
     */
    List<SysIpGroupDTO> findAll();


    /**
     * Get the "id" sysIpGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysIpGroupDTO> findOne(Long id);

    /**
     * Delete the "id" sysIpGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
