package arcturus.web.rest;

import arcturus.service.SysModuleService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysModuleDTO;

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
 * REST controller for managing {@link arcturus.domain.SysModule}.
 */
@RestController
@RequestMapping("/api")
public class SysModuleResource {

    private final Logger log = LoggerFactory.getLogger(SysModuleResource.class);

    private static final String ENTITY_NAME = "sysModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysModuleService sysModuleService;

    public SysModuleResource(SysModuleService sysModuleService) {
        this.sysModuleService = sysModuleService;
    }

    /**
     * {@code POST  /sys-modules} : Create a new sysModule.
     *
     * @param sysModuleDTO the sysModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysModuleDTO, or with status {@code 400 (Bad Request)} if the sysModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-modules")
    public ResponseEntity<SysModuleDTO> createSysModule(@RequestBody SysModuleDTO sysModuleDTO) throws URISyntaxException {
        log.debug("REST request to save SysModule : {}", sysModuleDTO);
        if (sysModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysModuleDTO result = sysModuleService.save(sysModuleDTO);
        return ResponseEntity.created(new URI("/api/sys-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-modules} : Updates an existing sysModule.
     *
     * @param sysModuleDTO the sysModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysModuleDTO,
     * or with status {@code 400 (Bad Request)} if the sysModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-modules")
    public ResponseEntity<SysModuleDTO> updateSysModule(@RequestBody SysModuleDTO sysModuleDTO) throws URISyntaxException {
        log.debug("REST request to update SysModule : {}", sysModuleDTO);
        if (sysModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysModuleDTO result = sysModuleService.save(sysModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-modules} : get all the sysModules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysModules in body.
     */
    @GetMapping("/sys-modules")
    public List<SysModuleDTO> getAllSysModules() {
        log.debug("REST request to get all SysModules");
        return sysModuleService.findAll();
    }

    /**
     * {@code GET  /sys-modules/:id} : get the "id" sysModule.
     *
     * @param id the id of the sysModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-modules/{id}")
    public ResponseEntity<SysModuleDTO> getSysModule(@PathVariable Long id) {
        log.debug("REST request to get SysModule : {}", id);
        Optional<SysModuleDTO> sysModuleDTO = sysModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysModuleDTO);
    }

    /**
     * {@code DELETE  /sys-modules/:id} : delete the "id" sysModule.
     *
     * @param id the id of the sysModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-modules/{id}")
    public ResponseEntity<Void> deleteSysModule(@PathVariable Long id) {
        log.debug("REST request to delete SysModule : {}", id);
        sysModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
