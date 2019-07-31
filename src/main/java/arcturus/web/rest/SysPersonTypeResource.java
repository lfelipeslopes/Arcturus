package arcturus.web.rest;

import arcturus.service.SysPersonTypeService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysPersonTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link arcturus.domain.SysPersonType}.
 */
@RestController
@RequestMapping("/api")
public class SysPersonTypeResource {

    private final Logger log = LoggerFactory.getLogger(SysPersonTypeResource.class);

    private static final String ENTITY_NAME = "sysPersonType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysPersonTypeService sysPersonTypeService;

    public SysPersonTypeResource(SysPersonTypeService sysPersonTypeService) {
        this.sysPersonTypeService = sysPersonTypeService;
    }

    /**
     * {@code POST  /sys-person-types} : Create a new sysPersonType.
     *
     * @param sysPersonTypeDTO the sysPersonTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysPersonTypeDTO, or with status {@code 400 (Bad Request)} if the sysPersonType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-person-types")
    public ResponseEntity<SysPersonTypeDTO> createSysPersonType(@RequestBody SysPersonTypeDTO sysPersonTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SysPersonType : {}", sysPersonTypeDTO);
        if (sysPersonTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysPersonType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysPersonTypeDTO result = sysPersonTypeService.save(sysPersonTypeDTO);
        return ResponseEntity.created(new URI("/api/sys-person-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-person-types} : Updates an existing sysPersonType.
     *
     * @param sysPersonTypeDTO the sysPersonTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysPersonTypeDTO,
     * or with status {@code 400 (Bad Request)} if the sysPersonTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysPersonTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-person-types")
    public ResponseEntity<SysPersonTypeDTO> updateSysPersonType(@RequestBody SysPersonTypeDTO sysPersonTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SysPersonType : {}", sysPersonTypeDTO);
        if (sysPersonTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysPersonTypeDTO result = sysPersonTypeService.save(sysPersonTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysPersonTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-person-types} : get all the sysPersonTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysPersonTypes in body.
     */
    @GetMapping("/sys-person-types")
    public List<SysPersonTypeDTO> getAllSysPersonTypes() {
        log.debug("REST request to get all SysPersonTypes");
        return sysPersonTypeService.findAll();
    }

    /**
     * {@code GET  /sys-person-types/:id} : get the "id" sysPersonType.
     *
     * @param id the id of the sysPersonTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysPersonTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-person-types/{id}")
    public ResponseEntity<SysPersonTypeDTO> getSysPersonType(@PathVariable Long id) {
        log.debug("REST request to get SysPersonType : {}", id);
        Optional<SysPersonTypeDTO> sysPersonTypeDTO = sysPersonTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysPersonTypeDTO);
    }

    /**
     * {@code DELETE  /sys-person-types/:id} : delete the "id" sysPersonType.
     *
     * @param id the id of the sysPersonTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-person-types/{id}")
    public ResponseEntity<Void> deleteSysPersonType(@PathVariable Long id) {
        log.debug("REST request to delete SysPersonType : {}", id);
        sysPersonTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
