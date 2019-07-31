package arcturus.service.impl;

import arcturus.service.SysIpGroupService;
import arcturus.domain.SysIpGroup;
import arcturus.repository.SysIpGroupRepository;
import arcturus.service.dto.SysIpGroupDTO;
import arcturus.service.mapper.SysIpGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysIpGroup}.
 */
@Service
@Transactional
public class SysIpGroupServiceImpl implements SysIpGroupService {

    private final Logger log = LoggerFactory.getLogger(SysIpGroupServiceImpl.class);

    private final SysIpGroupRepository sysIpGroupRepository;

    private final SysIpGroupMapper sysIpGroupMapper;

    public SysIpGroupServiceImpl(SysIpGroupRepository sysIpGroupRepository, SysIpGroupMapper sysIpGroupMapper) {
        this.sysIpGroupRepository = sysIpGroupRepository;
        this.sysIpGroupMapper = sysIpGroupMapper;
    }

    /**
     * Save a sysIpGroup.
     *
     * @param sysIpGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysIpGroupDTO save(SysIpGroupDTO sysIpGroupDTO) {
        log.debug("Request to save SysIpGroup : {}", sysIpGroupDTO);
        SysIpGroup sysIpGroup = sysIpGroupMapper.toEntity(sysIpGroupDTO);
        sysIpGroup = sysIpGroupRepository.save(sysIpGroup);
        return sysIpGroupMapper.toDto(sysIpGroup);
    }

    /**
     * Get all the sysIpGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysIpGroupDTO> findAll() {
        log.debug("Request to get all SysIpGroups");
        return sysIpGroupRepository.findAll().stream()
            .map(sysIpGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysIpGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysIpGroupDTO> findOne(Long id) {
        log.debug("Request to get SysIpGroup : {}", id);
        return sysIpGroupRepository.findById(id)
            .map(sysIpGroupMapper::toDto);
    }

    /**
     * Delete the sysIpGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysIpGroup : {}", id);
        sysIpGroupRepository.deleteById(id);
    }
}
