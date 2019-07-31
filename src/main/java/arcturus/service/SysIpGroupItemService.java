package arcturus.service;

import arcturus.service.dto.SysIpGroupItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysIpGroupItem}.
 */
public interface SysIpGroupItemService {

    /**
     * Save a sysIpGroupItem.
     *
     * @param sysIpGroupItemDTO the entity to save.
     * @return the persisted entity.
     */
    SysIpGroupItemDTO save(SysIpGroupItemDTO sysIpGroupItemDTO);

    /**
     * Get all the sysIpGroupItems.
     *
     * @return the list of entities.
     */
    List<SysIpGroupItemDTO> findAll();


    /**
     * Get the "id" sysIpGroupItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysIpGroupItemDTO> findOne(Long id);

    /**
     * Delete the "id" sysIpGroupItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
