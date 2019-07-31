package arcturus.service;

import arcturus.service.dto.SysLicenseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link arcturus.domain.SysLicense}.
 */
public interface SysLicenseService {

    /**
     * Save a sysLicense.
     *
     * @param sysLicenseDTO the entity to save.
     * @return the persisted entity.
     */
    SysLicenseDTO save(SysLicenseDTO sysLicenseDTO);

    /**
     * Get all the sysLicenses.
     *
     * @return the list of entities.
     */
    List<SysLicenseDTO> findAll();


    /**
     * Get the "id" sysLicense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysLicenseDTO> findOne(Long id);

    /**
     * Delete the "id" sysLicense.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
