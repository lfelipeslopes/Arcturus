package arcturus.service.impl;

import arcturus.service.SysEnterpriseService;
import arcturus.domain.SysEnterprise;
import arcturus.repository.SysEnterpriseRepository;
import arcturus.service.dto.SysEnterpriseDTO;
import arcturus.service.mapper.SysEnterpriseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysEnterprise}.
 */
@Service
@Transactional
public class SysEnterpriseServiceImpl implements SysEnterpriseService {

    private final Logger log = LoggerFactory.getLogger(SysEnterpriseServiceImpl.class);

    private final SysEnterpriseRepository sysEnterpriseRepository;

    private final SysEnterpriseMapper sysEnterpriseMapper;

    public SysEnterpriseServiceImpl(SysEnterpriseRepository sysEnterpriseRepository, SysEnterpriseMapper sysEnterpriseMapper) {
        this.sysEnterpriseRepository = sysEnterpriseRepository;
        this.sysEnterpriseMapper = sysEnterpriseMapper;
    }

    /**
     * Save a sysEnterprise.
     *
     * @param sysEnterpriseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysEnterpriseDTO save(SysEnterpriseDTO sysEnterpriseDTO) {
        log.debug("Request to save SysEnterprise : {}", sysEnterpriseDTO);
        SysEnterprise sysEnterprise = sysEnterpriseMapper.toEntity(sysEnterpriseDTO);
        sysEnterprise = sysEnterpriseRepository.save(sysEnterprise);
        return sysEnterpriseMapper.toDto(sysEnterprise);
    }

    /**
     * Get all the sysEnterprises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysEnterpriseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysEnterprises");
        return sysEnterpriseRepository.findAll(pageable)
            .map(sysEnterpriseMapper::toDto);
    }

    /**
     * Get all the sysEnterprises with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SysEnterpriseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sysEnterpriseRepository.findAllWithEagerRelationships(pageable).map(sysEnterpriseMapper::toDto);
    }
    

    /**
     * Get one sysEnterprise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysEnterpriseDTO> findOne(Long id) {
        log.debug("Request to get SysEnterprise : {}", id);
        return sysEnterpriseRepository.findOneWithEagerRelationships(id)
            .map(sysEnterpriseMapper::toDto);
    }

    /**
     * Delete the sysEnterprise by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysEnterprise : {}", id);
        sysEnterpriseRepository.deleteById(id);
    }
}
