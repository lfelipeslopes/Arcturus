package arcturus.service.impl;

import arcturus.service.SysIpGroupItemService;
import arcturus.domain.SysIpGroupItem;
import arcturus.repository.SysIpGroupItemRepository;
import arcturus.service.dto.SysIpGroupItemDTO;
import arcturus.service.mapper.SysIpGroupItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysIpGroupItem}.
 */
@Service
@Transactional
public class SysIpGroupItemServiceImpl implements SysIpGroupItemService {

    private final Logger log = LoggerFactory.getLogger(SysIpGroupItemServiceImpl.class);

    private final SysIpGroupItemRepository sysIpGroupItemRepository;

    private final SysIpGroupItemMapper sysIpGroupItemMapper;

    public SysIpGroupItemServiceImpl(SysIpGroupItemRepository sysIpGroupItemRepository, SysIpGroupItemMapper sysIpGroupItemMapper) {
        this.sysIpGroupItemRepository = sysIpGroupItemRepository;
        this.sysIpGroupItemMapper = sysIpGroupItemMapper;
    }

    /**
     * Save a sysIpGroupItem.
     *
     * @param sysIpGroupItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysIpGroupItemDTO save(SysIpGroupItemDTO sysIpGroupItemDTO) {
        log.debug("Request to save SysIpGroupItem : {}", sysIpGroupItemDTO);
        SysIpGroupItem sysIpGroupItem = sysIpGroupItemMapper.toEntity(sysIpGroupItemDTO);
        sysIpGroupItem = sysIpGroupItemRepository.save(sysIpGroupItem);
        return sysIpGroupItemMapper.toDto(sysIpGroupItem);
    }

    /**
     * Get all the sysIpGroupItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysIpGroupItemDTO> findAll() {
        log.debug("Request to get all SysIpGroupItems");
        return sysIpGroupItemRepository.findAll().stream()
            .map(sysIpGroupItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysIpGroupItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysIpGroupItemDTO> findOne(Long id) {
        log.debug("Request to get SysIpGroupItem : {}", id);
        return sysIpGroupItemRepository.findById(id)
            .map(sysIpGroupItemMapper::toDto);
    }

    /**
     * Delete the sysIpGroupItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysIpGroupItem : {}", id);
        sysIpGroupItemRepository.deleteById(id);
    }
}
