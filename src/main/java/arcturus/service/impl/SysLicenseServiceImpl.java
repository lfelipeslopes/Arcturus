package arcturus.service.impl;

import arcturus.service.SysLicenseService;
import arcturus.domain.SysLicense;
import arcturus.repository.SysLicenseRepository;
import arcturus.service.dto.SysLicenseDTO;
import arcturus.service.mapper.SysLicenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysLicense}.
 */
@Service
@Transactional
public class SysLicenseServiceImpl implements SysLicenseService {

    private final Logger log = LoggerFactory.getLogger(SysLicenseServiceImpl.class);

    private final SysLicenseRepository sysLicenseRepository;

    private final SysLicenseMapper sysLicenseMapper;

    public SysLicenseServiceImpl(SysLicenseRepository sysLicenseRepository, SysLicenseMapper sysLicenseMapper) {
        this.sysLicenseRepository = sysLicenseRepository;
        this.sysLicenseMapper = sysLicenseMapper;
    }

    /**
     * Save a sysLicense.
     *
     * @param sysLicenseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysLicenseDTO save(SysLicenseDTO sysLicenseDTO) {
        log.debug("Request to save SysLicense : {}", sysLicenseDTO);
        SysLicense sysLicense = sysLicenseMapper.toEntity(sysLicenseDTO);
        sysLicense = sysLicenseRepository.save(sysLicense);
        return sysLicenseMapper.toDto(sysLicense);
    }

    /**
     * Get all the sysLicenses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysLicenseDTO> findAll() {
        log.debug("Request to get all SysLicenses");
        return sysLicenseRepository.findAll().stream()
            .map(sysLicenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysLicense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysLicenseDTO> findOne(Long id) {
        log.debug("Request to get SysLicense : {}", id);
        return sysLicenseRepository.findById(id)
            .map(sysLicenseMapper::toDto);
    }

    /**
     * Delete the sysLicense by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysLicense : {}", id);
        sysLicenseRepository.deleteById(id);
    }
}
