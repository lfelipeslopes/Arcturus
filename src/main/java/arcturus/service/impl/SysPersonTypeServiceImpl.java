package arcturus.service.impl;

import arcturus.service.SysPersonTypeService;
import arcturus.domain.SysPersonType;
import arcturus.repository.SysPersonTypeRepository;
import arcturus.service.dto.SysPersonTypeDTO;
import arcturus.service.mapper.SysPersonTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysPersonType}.
 */
@Service
@Transactional
public class SysPersonTypeServiceImpl implements SysPersonTypeService {

    private final Logger log = LoggerFactory.getLogger(SysPersonTypeServiceImpl.class);

    private final SysPersonTypeRepository sysPersonTypeRepository;

    private final SysPersonTypeMapper sysPersonTypeMapper;

    public SysPersonTypeServiceImpl(SysPersonTypeRepository sysPersonTypeRepository, SysPersonTypeMapper sysPersonTypeMapper) {
        this.sysPersonTypeRepository = sysPersonTypeRepository;
        this.sysPersonTypeMapper = sysPersonTypeMapper;
    }

    /**
     * Save a sysPersonType.
     *
     * @param sysPersonTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysPersonTypeDTO save(SysPersonTypeDTO sysPersonTypeDTO) {
        log.debug("Request to save SysPersonType : {}", sysPersonTypeDTO);
        SysPersonType sysPersonType = sysPersonTypeMapper.toEntity(sysPersonTypeDTO);
        sysPersonType = sysPersonTypeRepository.save(sysPersonType);
        return sysPersonTypeMapper.toDto(sysPersonType);
    }

    /**
     * Get all the sysPersonTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPersonTypeDTO> findAll() {
        log.debug("Request to get all SysPersonTypes");
        return sysPersonTypeRepository.findAll().stream()
            .map(sysPersonTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysPersonType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysPersonTypeDTO> findOne(Long id) {
        log.debug("Request to get SysPersonType : {}", id);
        return sysPersonTypeRepository.findById(id)
            .map(sysPersonTypeMapper::toDto);
    }

    /**
     * Delete the sysPersonType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysPersonType : {}", id);
        sysPersonTypeRepository.deleteById(id);
    }
}
