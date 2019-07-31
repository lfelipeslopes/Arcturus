package arcturus.service.impl;

import arcturus.service.SysGroupService;
import arcturus.domain.SysGroup;
import arcturus.repository.SysGroupRepository;
import arcturus.service.dto.SysGroupDTO;
import arcturus.service.mapper.SysGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysGroup}.
 */
@Service
@Transactional
public class SysGroupServiceImpl implements SysGroupService {

    private final Logger log = LoggerFactory.getLogger(SysGroupServiceImpl.class);

    private final SysGroupRepository sysGroupRepository;

    private final SysGroupMapper sysGroupMapper;

    public SysGroupServiceImpl(SysGroupRepository sysGroupRepository, SysGroupMapper sysGroupMapper) {
        this.sysGroupRepository = sysGroupRepository;
        this.sysGroupMapper = sysGroupMapper;
    }

    /**
     * Save a sysGroup.
     *
     * @param sysGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysGroupDTO save(SysGroupDTO sysGroupDTO) {
        log.debug("Request to save SysGroup : {}", sysGroupDTO);
        SysGroup sysGroup = sysGroupMapper.toEntity(sysGroupDTO);
        sysGroup = sysGroupRepository.save(sysGroup);
        return sysGroupMapper.toDto(sysGroup);
    }

    /**
     * Get all the sysGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysGroups");
        return sysGroupRepository.findAll(pageable)
            .map(sysGroupMapper::toDto);
    }

    /**
     * Get all the sysGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SysGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sysGroupRepository.findAllWithEagerRelationships(pageable).map(sysGroupMapper::toDto);
    }
    

    /**
     * Get one sysGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysGroupDTO> findOne(Long id) {
        log.debug("Request to get SysGroup : {}", id);
        return sysGroupRepository.findOneWithEagerRelationships(id)
            .map(sysGroupMapper::toDto);
    }

    /**
     * Delete the sysGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysGroup : {}", id);
        sysGroupRepository.deleteById(id);
    }
}
