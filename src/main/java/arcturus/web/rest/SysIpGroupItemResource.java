package arcturus.web.rest;

import arcturus.service.SysIpGroupItemService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysIpGroupItemDTO;

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
 * REST controller for managing {@link arcturus.domain.SysIpGroupItem}.
 */
@RestController
@RequestMapping("/api")
public class SysIpGroupItemResource {

    private final Logger log = LoggerFactory.getLogger(SysIpGroupItemResource.class);

    private static final String ENTITY_NAME = "sysIpGroupItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysIpGroupItemService sysIpGroupItemService;

    public SysIpGroupItemResource(SysIpGroupItemService sysIpGroupItemService) {
        this.sysIpGroupItemService = sysIpGroupItemService;
    }

    /**
     * {@code POST  /sys-ip-group-items} : Create a new sysIpGroupItem.
     *
     * @param sysIpGroupItemDTO the sysIpGroupItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysIpGroupItemDTO, or with status {@code 400 (Bad Request)} if the sysIpGroupItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-ip-group-items")
    public ResponseEntity<SysIpGroupItemDTO> createSysIpGroupItem(@RequestBody SysIpGroupItemDTO sysIpGroupItemDTO) throws URISyntaxException {
        log.debug("REST request to save SysIpGroupItem : {}", sysIpGroupItemDTO);
        if (sysIpGroupItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysIpGroupItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysIpGroupItemDTO result = sysIpGroupItemService.save(sysIpGroupItemDTO);
        return ResponseEntity.created(new URI("/api/sys-ip-group-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-ip-group-items} : Updates an existing sysIpGroupItem.
     *
     * @param sysIpGroupItemDTO the sysIpGroupItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysIpGroupItemDTO,
     * or with status {@code 400 (Bad Request)} if the sysIpGroupItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysIpGroupItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-ip-group-items")
    public ResponseEntity<SysIpGroupItemDTO> updateSysIpGroupItem(@RequestBody SysIpGroupItemDTO sysIpGroupItemDTO) throws URISyntaxException {
        log.debug("REST request to update SysIpGroupItem : {}", sysIpGroupItemDTO);
        if (sysIpGroupItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysIpGroupItemDTO result = sysIpGroupItemService.save(sysIpGroupItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysIpGroupItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-ip-group-items} : get all the sysIpGroupItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysIpGroupItems in body.
     */
    @GetMapping("/sys-ip-group-items")
    public List<SysIpGroupItemDTO> getAllSysIpGroupItems() {
        log.debug("REST request to get all SysIpGroupItems");
        return sysIpGroupItemService.findAll();
    }

    /**
     * {@code GET  /sys-ip-group-items/:id} : get the "id" sysIpGroupItem.
     *
     * @param id the id of the sysIpGroupItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysIpGroupItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-ip-group-items/{id}")
    public ResponseEntity<SysIpGroupItemDTO> getSysIpGroupItem(@PathVariable Long id) {
        log.debug("REST request to get SysIpGroupItem : {}", id);
        Optional<SysIpGroupItemDTO> sysIpGroupItemDTO = sysIpGroupItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysIpGroupItemDTO);
    }

    /**
     * {@code DELETE  /sys-ip-group-items/:id} : delete the "id" sysIpGroupItem.
     *
     * @param id the id of the sysIpGroupItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-ip-group-items/{id}")
    public ResponseEntity<Void> deleteSysIpGroupItem(@PathVariable Long id) {
        log.debug("REST request to delete SysIpGroupItem : {}", id);
        sysIpGroupItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
