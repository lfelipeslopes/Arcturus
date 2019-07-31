package arcturus.service;

import arcturus.service.dto.SysPersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysPerson}.
 */
public interface SysPersonService {

    /**
     * Save a sysPerson.
     *
     * @param sysPersonDTO the entity to save.
     * @return the persisted entity.
     */
    SysPersonDTO save(SysPersonDTO sysPersonDTO);

    /**
     * Get all the sysPeople.
     *
     * @return the list of entities.
     */
    List<SysPersonDTO> findAll();


    /**
     * Get the "id" sysPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysPersonDTO> findOne(Long id);

    /**
     * Delete the "id" sysPerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
