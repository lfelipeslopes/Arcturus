package arcturus.service.impl;

import arcturus.service.SysAccessService;
import arcturus.domain.SysAccess;
import arcturus.repository.SysAccessRepository;
import arcturus.service.dto.SysAccessDTO;
import arcturus.service.mapper.SysAccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysAccess}.
 */
@Service
@Transactional
public class SysAccessServiceImpl implements SysAccessService {

    private final Logger log = LoggerFactory.getLogger(SysAccessServiceImpl.class);

    private final SysAccessRepository sysAccessRepository;

    private final SysAccessMapper sysAccessMapper;

    public SysAccessServiceImpl(SysAccessRepository sysAccessRepository, SysAccessMapper sysAccessMapper) {
        this.sysAccessRepository = sysAccessRepository;
        this.sysAccessMapper = sysAccessMapper;
    }

    /**
     * Save a sysAccess.
     *
     * @param sysAccessDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysAccessDTO save(SysAccessDTO sysAccessDTO) {
        log.debug("Request to save SysAccess : {}", sysAccessDTO);
        SysAccess sysAccess = sysAccessMapper.toEntity(sysAccessDTO);
        sysAccess = sysAccessRepository.save(sysAccess);
        return sysAccessMapper.toDto(sysAccess);
    }

    /**
     * Get all the sysAccesses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysAccessDTO> findAll() {
        log.debug("Request to get all SysAccesses");
        return sysAccessRepository.findAll().stream()
            .map(sysAccessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysAccess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysAccessDTO> findOne(Long id) {
        log.debug("Request to get SysAccess : {}", id);
        return sysAccessRepository.findById(id)
            .map(sysAccessMapper::toDto);
    }

    /**
     * Delete the sysAccess by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysAccess : {}", id);
        sysAccessRepository.deleteById(id);
    }
}
