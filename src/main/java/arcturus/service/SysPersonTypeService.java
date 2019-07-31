package arcturus.service;

import arcturus.service.dto.SysPersonTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysPersonType}.
 */
public interface SysPersonTypeService {

    /**
     * Save a sysPersonType.
     *
     * @param sysPersonTypeDTO the entity to save.
     * @return the persisted entity.
     */
    SysPersonTypeDTO save(SysPersonTypeDTO sysPersonTypeDTO);

    /**
     * Get all the sysPersonTypes.
     *
     * @return the list of entities.
     */
    List<SysPersonTypeDTO> findAll();


    /**
     * Get the "id" sysPersonType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysPersonTypeDTO> findOne(Long id);

    /**
     * Delete the "id" sysPersonType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
