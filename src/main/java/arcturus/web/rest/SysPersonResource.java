package arcturus.web.rest;

import arcturus.service.SysPersonService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysPersonDTO;

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
 * REST controller for managing {@link arcturus.domain.SysPerson}.
 */
@RestController
@RequestMapping("/api")
public class SysPersonResource {

    private final Logger log = LoggerFactory.getLogger(SysPersonResource.class);

    private static final String ENTITY_NAME = "sysPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysPersonService sysPersonService;

    public SysPersonResource(SysPersonService sysPersonService) {
        this.sysPersonService = sysPersonService;
    }

    /**
     * {@code POST  /sys-people} : Create a new sysPerson.
     *
     * @param sysPersonDTO the sysPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysPersonDTO, or with status {@code 400 (Bad Request)} if the sysPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-people")
    public ResponseEntity<SysPersonDTO> createSysPerson(@RequestBody SysPersonDTO sysPersonDTO) throws URISyntaxException {
        log.debug("REST request to save SysPerson : {}", sysPersonDTO);
        if (sysPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysPersonDTO result = sysPersonService.save(sysPersonDTO);
        return ResponseEntity.created(new URI("/api/sys-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-people} : Updates an existing sysPerson.
     *
     * @param sysPersonDTO the sysPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysPersonDTO,
     * or with status {@code 400 (Bad Request)} if the sysPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-people")
    public ResponseEntity<SysPersonDTO> updateSysPerson(@RequestBody SysPersonDTO sysPersonDTO) throws URISyntaxException {
        log.debug("REST request to update SysPerson : {}", sysPersonDTO);
        if (sysPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysPersonDTO result = sysPersonService.save(sysPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-people} : get all the sysPeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysPeople in body.
     */
    @GetMapping("/sys-people")
    public List<SysPersonDTO> getAllSysPeople() {
        log.debug("REST request to get all SysPeople");
        return sysPersonService.findAll();
    }

    /**
     * {@code GET  /sys-people/:id} : get the "id" sysPerson.
     *
     * @param id the id of the sysPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-people/{id}")
    public ResponseEntity<SysPersonDTO> getSysPerson(@PathVariable Long id) {
        log.debug("REST request to get SysPerson : {}", id);
        Optional<SysPersonDTO> sysPersonDTO = sysPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysPersonDTO);
    }

    /**
     * {@code DELETE  /sys-people/:id} : delete the "id" sysPerson.
     *
     * @param id the id of the sysPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-people/{id}")
    public ResponseEntity<Void> deleteSysPerson(@PathVariable Long id) {
        log.debug("REST request to delete SysPerson : {}", id);
        sysPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
