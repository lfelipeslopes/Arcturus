package arcturus.web.rest;

import arcturus.service.SysAccessService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysAccessDTO;

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
 * REST controller for managing {@link arcturus.domain.SysAccess}.
 */
@RestController
@RequestMapping("/api")
public class SysAccessResource {

    private final Logger log = LoggerFactory.getLogger(SysAccessResource.class);

    private static final String ENTITY_NAME = "sysAccess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysAccessService sysAccessService;

    public SysAccessResource(SysAccessService sysAccessService) {
        this.sysAccessService = sysAccessService;
    }

    /**
     * {@code POST  /sys-accesses} : Create a new sysAccess.
     *
     * @param sysAccessDTO the sysAccessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysAccessDTO, or with status {@code 400 (Bad Request)} if the sysAccess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-accesses")
    public ResponseEntity<SysAccessDTO> createSysAccess(@RequestBody SysAccessDTO sysAccessDTO) throws URISyntaxException {
        log.debug("REST request to save SysAccess : {}", sysAccessDTO);
        if (sysAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysAccessDTO result = sysAccessService.save(sysAccessDTO);
        return ResponseEntity.created(new URI("/api/sys-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-accesses} : Updates an existing sysAccess.
     *
     * @param sysAccessDTO the sysAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysAccessDTO,
     * or with status {@code 400 (Bad Request)} if the sysAccessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-accesses")
    public ResponseEntity<SysAccessDTO> updateSysAccess(@RequestBody SysAccessDTO sysAccessDTO) throws URISyntaxException {
        log.debug("REST request to update SysAccess : {}", sysAccessDTO);
        if (sysAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysAccessDTO result = sysAccessService.save(sysAccessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-accesses} : get all the sysAccesses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysAccesses in body.
     */
    @GetMapping("/sys-accesses")
    public List<SysAccessDTO> getAllSysAccesses() {
        log.debug("REST request to get all SysAccesses");
        return sysAccessService.findAll();
    }

    /**
     * {@code GET  /sys-accesses/:id} : get the "id" sysAccess.
     *
     * @param id the id of the sysAccessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysAccessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-accesses/{id}")
    public ResponseEntity<SysAccessDTO> getSysAccess(@PathVariable Long id) {
        log.debug("REST request to get SysAccess : {}", id);
        Optional<SysAccessDTO> sysAccessDTO = sysAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysAccessDTO);
    }

    /**
     * {@code DELETE  /sys-accesses/:id} : delete the "id" sysAccess.
     *
     * @param id the id of the sysAccessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-accesses/{id}")
    public ResponseEntity<Void> deleteSysAccess(@PathVariable Long id) {
        log.debug("REST request to delete SysAccess : {}", id);
        sysAccessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
