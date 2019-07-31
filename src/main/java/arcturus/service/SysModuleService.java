package arcturus.service;

import arcturus.service.dto.SysModuleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysModule}.
 */
public interface SysModuleService {

    /**
     * Save a sysModule.
     *
     * @param sysModuleDTO the entity to save.
     * @return the persisted entity.
     */
    SysModuleDTO save(SysModuleDTO sysModuleDTO);

    /**
     * Get all the sysModules.
     *
     * @return the list of entities.
     */
    List<SysModuleDTO> findAll();


    /**
     * Get the "id" sysModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysModuleDTO> findOne(Long id);

    /**
     * Delete the "id" sysModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
