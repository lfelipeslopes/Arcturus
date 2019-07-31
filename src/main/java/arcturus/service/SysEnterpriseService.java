package arcturus.service;

import arcturus.service.dto.SysEnterpriseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysEnterprise}.
 */
public interface SysEnterpriseService {

    /**
     * Save a sysEnterprise.
     *
     * @param sysEnterpriseDTO the entity to save.
     * @return the persisted entity.
     */
    SysEnterpriseDTO save(SysEnterpriseDTO sysEnterpriseDTO);

    /**
     * Get all the sysEnterprises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysEnterpriseDTO> findAll(Pageable pageable);

    /**
     * Get all the sysEnterprises with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SysEnterpriseDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" sysEnterprise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysEnterpriseDTO> findOne(Long id);

    /**
     * Delete the "id" sysEnterprise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
