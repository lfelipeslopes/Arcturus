package arcturus.web.rest;

import arcturus.service.SysIpGroupService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysIpGroupDTO;

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
 * REST controller for managing {@link arcturus.domain.SysIpGroup}.
 */
@RestController
@RequestMapping("/api")
public class SysIpGroupResource {

    private final Logger log = LoggerFactory.getLogger(SysIpGroupResource.class);

    private static final String ENTITY_NAME = "sysIpGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysIpGroupService sysIpGroupService;

    public SysIpGroupResource(SysIpGroupService sysIpGroupService) {
        this.sysIpGroupService = sysIpGroupService;
    }

    /**
     * {@code POST  /sys-ip-groups} : Create a new sysIpGroup.
     *
     * @param sysIpGroupDTO the sysIpGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysIpGroupDTO, or with status {@code 400 (Bad Request)} if the sysIpGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-ip-groups")
    public ResponseEntity<SysIpGroupDTO> createSysIpGroup(@RequestBody SysIpGroupDTO sysIpGroupDTO) throws URISyntaxException {
        log.debug("REST request to save SysIpGroup : {}", sysIpGroupDTO);
        if (sysIpGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysIpGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysIpGroupDTO result = sysIpGroupService.save(sysIpGroupDTO);
        return ResponseEntity.created(new URI("/api/sys-ip-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-ip-groups} : Updates an existing sysIpGroup.
     *
     * @param sysIpGroupDTO the sysIpGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysIpGroupDTO,
     * or with status {@code 400 (Bad Request)} if the sysIpGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysIpGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-ip-groups")
    public ResponseEntity<SysIpGroupDTO> updateSysIpGroup(@RequestBody SysIpGroupDTO sysIpGroupDTO) throws URISyntaxException {
        log.debug("REST request to update SysIpGroup : {}", sysIpGroupDTO);
        if (sysIpGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysIpGroupDTO result = sysIpGroupService.save(sysIpGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysIpGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-ip-groups} : get all the sysIpGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysIpGroups in body.
     */
    @GetMapping("/sys-ip-groups")
    public List<SysIpGroupDTO> getAllSysIpGroups() {
        log.debug("REST request to get all SysIpGroups");
        return sysIpGroupService.findAll();
    }

    /**
     * {@code GET  /sys-ip-groups/:id} : get the "id" sysIpGroup.
     *
     * @param id the id of the sysIpGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysIpGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-ip-groups/{id}")
    public ResponseEntity<SysIpGroupDTO> getSysIpGroup(@PathVariable Long id) {
        log.debug("REST request to get SysIpGroup : {}", id);
        Optional<SysIpGroupDTO> sysIpGroupDTO = sysIpGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysIpGroupDTO);
    }

    /**
     * {@code DELETE  /sys-ip-groups/:id} : delete the "id" sysIpGroup.
     *
     * @param id the id of the sysIpGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-ip-groups/{id}")
    public ResponseEntity<Void> deleteSysIpGroup(@PathVariable Long id) {
        log.debug("REST request to delete SysIpGroup : {}", id);
        sysIpGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
