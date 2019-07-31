package arcturus.service.impl;

import arcturus.service.SysModuleService;
import arcturus.domain.SysModule;
import arcturus.repository.SysModuleRepository;
import arcturus.service.dto.SysModuleDTO;
import arcturus.service.mapper.SysModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysModule}.
 */
@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

    private final Logger log = LoggerFactory.getLogger(SysModuleServiceImpl.class);

    private final SysModuleRepository sysModuleRepository;

    private final SysModuleMapper sysModuleMapper;

    public SysModuleServiceImpl(SysModuleRepository sysModuleRepository, SysModuleMapper sysModuleMapper) {
        this.sysModuleRepository = sysModuleRepository;
        this.sysModuleMapper = sysModuleMapper;
    }

    /**
     * Save a sysModule.
     *
     * @param sysModuleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysModuleDTO save(SysModuleDTO sysModuleDTO) {
        log.debug("Request to save SysModule : {}", sysModuleDTO);
        SysModule sysModule = sysModuleMapper.toEntity(sysModuleDTO);
        sysModule = sysModuleRepository.save(sysModule);
        return sysModuleMapper.toDto(sysModule);
    }

    /**
     * Get all the sysModules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysModuleDTO> findAll() {
        log.debug("Request to get all SysModules");
        return sysModuleRepository.findAll().stream()
            .map(sysModuleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysModule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysModuleDTO> findOne(Long id) {
        log.debug("Request to get SysModule : {}", id);
        return sysModuleRepository.findById(id)
            .map(sysModuleMapper::toDto);
    }

    /**
     * Delete the sysModule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysModule : {}", id);
        sysModuleRepository.deleteById(id);
    }
}
