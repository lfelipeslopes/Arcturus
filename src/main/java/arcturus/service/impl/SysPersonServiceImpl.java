package arcturus.service.impl;

import arcturus.service.SysPersonService;
import arcturus.domain.SysPerson;
import arcturus.repository.SysPersonRepository;
import arcturus.service.dto.SysPersonDTO;
import arcturus.service.mapper.SysPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SysPerson}.
 */
@Service
@Transactional
public class SysPersonServiceImpl implements SysPersonService {

    private final Logger log = LoggerFactory.getLogger(SysPersonServiceImpl.class);

    private final SysPersonRepository sysPersonRepository;

    private final SysPersonMapper sysPersonMapper;

    public SysPersonServiceImpl(SysPersonRepository sysPersonRepository, SysPersonMapper sysPersonMapper) {
        this.sysPersonRepository = sysPersonRepository;
        this.sysPersonMapper = sysPersonMapper;
    }

    /**
     * Save a sysPerson.
     *
     * @param sysPersonDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysPersonDTO save(SysPersonDTO sysPersonDTO) {
        log.debug("Request to save SysPerson : {}", sysPersonDTO);
        SysPerson sysPerson = sysPersonMapper.toEntity(sysPersonDTO);
        sysPerson = sysPersonRepository.save(sysPerson);
        return sysPersonMapper.toDto(sysPerson);
    }

    /**
     * Get all the sysPeople.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPersonDTO> findAll() {
        log.debug("Request to get all SysPeople");
        return sysPersonRepository.findAll().stream()
            .map(sysPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sysPerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysPersonDTO> findOne(Long id) {
        log.debug("Request to get SysPerson : {}", id);
        return sysPersonRepository.findById(id)
            .map(sysPersonMapper::toDto);
    }

    /**
     * Delete the sysPerson by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysPerson : {}", id);
        sysPersonRepository.deleteById(id);
    }
}
