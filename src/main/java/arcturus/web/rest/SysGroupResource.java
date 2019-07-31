package arcturus.web.rest;

import arcturus.service.SysGroupService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link arcturus.domain.SysGroup}.
 */
@RestController
@RequestMapping("/api")
public class SysGroupResource {

    private final Logger log = LoggerFactory.getLogger(SysGroupResource.class);

    private static final String ENTITY_NAME = "sysGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysGroupService sysGroupService;

    public SysGroupResource(SysGroupService sysGroupService) {
        this.sysGroupService = sysGroupService;
    }

    /**
     * {@code POST  /sys-groups} : Create a new sysGroup.
     *
     * @param sysGroupDTO the sysGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysGroupDTO, or with status {@code 400 (Bad Request)} if the sysGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-groups")
    public ResponseEntity<SysGroupDTO> createSysGroup(@RequestBody SysGroupDTO sysGroupDTO) throws URISyntaxException {
        log.debug("REST request to save SysGroup : {}", sysGroupDTO);
        if (sysGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysGroupDTO result = sysGroupService.save(sysGroupDTO);
        return ResponseEntity.created(new URI("/api/sys-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-groups} : Updates an existing sysGroup.
     *
     * @param sysGroupDTO the sysGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysGroupDTO,
     * or with status {@code 400 (Bad Request)} if the sysGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-groups")
    public ResponseEntity<SysGroupDTO> updateSysGroup(@RequestBody SysGroupDTO sysGroupDTO) throws URISyntaxException {
        log.debug("REST request to update SysGroup : {}", sysGroupDTO);
        if (sysGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysGroupDTO result = sysGroupService.save(sysGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-groups} : get all the sysGroups.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysGroups in body.
     */
    @GetMapping("/sys-groups")
    public ResponseEntity<List<SysGroupDTO>> getAllSysGroups(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of SysGroups");
        Page<SysGroupDTO> page;
        if (eagerload) {
            page = sysGroupService.findAllWithEagerRelationships(pageable);
        } else {
            page = sysGroupService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-groups/:id} : get the "id" sysGroup.
     *
     * @param id the id of the sysGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-groups/{id}")
    public ResponseEntity<SysGroupDTO> getSysGroup(@PathVariable Long id) {
        log.debug("REST request to get SysGroup : {}", id);
        Optional<SysGroupDTO> sysGroupDTO = sysGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysGroupDTO);
    }

    /**
     * {@code DELETE  /sys-groups/:id} : delete the "id" sysGroup.
     *
     * @param id the id of the sysGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-groups/{id}")
    public ResponseEntity<Void> deleteSysGroup(@PathVariable Long id) {
        log.debug("REST request to delete SysGroup : {}", id);
        sysGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
