package arcturus.web.rest;

import arcturus.service.SysLicenseService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysLicenseDTO;

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
 * REST controller for managing {@link arcturus.domain.SysLicense}.
 */
@RestController
@RequestMapping("/api")
public class SysLicenseResource {

    private final Logger log = LoggerFactory.getLogger(SysLicenseResource.class);

    private static final String ENTITY_NAME = "sysLicense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysLicenseService sysLicenseService;

    public SysLicenseResource(SysLicenseService sysLicenseService) {
        this.sysLicenseService = sysLicenseService;
    }

    /**
     * {@code POST  /sys-licenses} : Create a new sysLicense.
     *
     * @param sysLicenseDTO the sysLicenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysLicenseDTO, or with status {@code 400 (Bad Request)} if the sysLicense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-licenses")
    public ResponseEntity<SysLicenseDTO> createSysLicense(@RequestBody SysLicenseDTO sysLicenseDTO) throws URISyntaxException {
        log.debug("REST request to save SysLicense : {}", sysLicenseDTO);
        if (sysLicenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysLicense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysLicenseDTO result = sysLicenseService.save(sysLicenseDTO);
        return ResponseEntity.created(new URI("/api/sys-licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-licenses} : Updates an existing sysLicense.
     *
     * @param sysLicenseDTO the sysLicenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysLicenseDTO,
     * or with status {@code 400 (Bad Request)} if the sysLicenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysLicenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-licenses")
    public ResponseEntity<SysLicenseDTO> updateSysLicense(@RequestBody SysLicenseDTO sysLicenseDTO) throws URISyntaxException {
        log.debug("REST request to update SysLicense : {}", sysLicenseDTO);
        if (sysLicenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysLicenseDTO result = sysLicenseService.save(sysLicenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysLicenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-licenses} : get all the sysLicenses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysLicenses in body.
     */
    @GetMapping("/sys-licenses")
    public List<SysLicenseDTO> getAllSysLicenses() {
        log.debug("REST request to get all SysLicenses");
        return sysLicenseService.findAll();
    }

    /**
     * {@code GET  /sys-licenses/:id} : get the "id" sysLicense.
     *
     * @param id the id of the sysLicenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysLicenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-licenses/{id}")
    public ResponseEntity<SysLicenseDTO> getSysLicense(@PathVariable Long id) {
        log.debug("REST request to get SysLicense : {}", id);
        Optional<SysLicenseDTO> sysLicenseDTO = sysLicenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysLicenseDTO);
    }

    /**
     * {@code DELETE  /sys-licenses/:id} : delete the "id" sysLicense.
     *
     * @param id the id of the sysLicenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-licenses/{id}")
    public ResponseEntity<Void> deleteSysLicense(@PathVariable Long id) {
        log.debug("REST request to delete SysLicense : {}", id);
        sysLicenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
