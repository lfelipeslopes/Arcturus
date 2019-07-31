package arcturus.service;

import arcturus.service.dto.SysAccessDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysAccess}.
 */
public interface SysAccessService {

    /**
     * Save a sysAccess.
     *
     * @param sysAccessDTO the entity to save.
     * @return the persisted entity.
     */
    SysAccessDTO save(SysAccessDTO sysAccessDTO);

    /**
     * Get all the sysAccesses.
     *
     * @return the list of entities.
     */
    List<SysAccessDTO> findAll();


    /**
     * Get the "id" sysAccess.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysAccessDTO> findOne(Long id);

    /**
     * Delete the "id" sysAccess.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
