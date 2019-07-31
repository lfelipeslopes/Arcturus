package arcturus.web.rest;

import arcturus.service.SysEnterpriseService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysEnterpriseDTO;

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
 * REST controller for managing {@link arcturus.domain.SysEnterprise}.
 */
@RestController
@RequestMapping("/api")
public class SysEnterpriseResource {

    private final Logger log = LoggerFactory.getLogger(SysEnterpriseResource.class);

    private static final String ENTITY_NAME = "sysEnterprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysEnterpriseService sysEnterpriseService;

    public SysEnterpriseResource(SysEnterpriseService sysEnterpriseService) {
        this.sysEnterpriseService = sysEnterpriseService;
    }

    /**
     * {@code POST  /sys-enterprises} : Create a new sysEnterprise.
     *
     * @param sysEnterpriseDTO the sysEnterpriseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysEnterpriseDTO, or with status {@code 400 (Bad Request)} if the sysEnterprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-enterprises")
    public ResponseEntity<SysEnterpriseDTO> createSysEnterprise(@RequestBody SysEnterpriseDTO sysEnterpriseDTO) throws URISyntaxException {
        log.debug("REST request to save SysEnterprise : {}", sysEnterpriseDTO);
        if (sysEnterpriseDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysEnterprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysEnterpriseDTO result = sysEnterpriseService.save(sysEnterpriseDTO);
        return ResponseEntity.created(new URI("/api/sys-enterprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-enterprises} : Updates an existing sysEnterprise.
     *
     * @param sysEnterpriseDTO the sysEnterpriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysEnterpriseDTO,
     * or with status {@code 400 (Bad Request)} if the sysEnterpriseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysEnterpriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-enterprises")
    public ResponseEntity<SysEnterpriseDTO> updateSysEnterprise(@RequestBody SysEnterpriseDTO sysEnterpriseDTO) throws URISyntaxException {
        log.debug("REST request to update SysEnterprise : {}", sysEnterpriseDTO);
        if (sysEnterpriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysEnterpriseDTO result = sysEnterpriseService.save(sysEnterpriseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysEnterpriseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-enterprises} : get all the sysEnterprises.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysEnterprises in body.
     */
    @GetMapping("/sys-enterprises")
    public ResponseEntity<List<SysEnterpriseDTO>> getAllSysEnterprises(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of SysEnterprises");
        Page<SysEnterpriseDTO> page;
        if (eagerload) {
            page = sysEnterpriseService.findAllWithEagerRelationships(pageable);
        } else {
            page = sysEnterpriseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-enterprises/:id} : get the "id" sysEnterprise.
     *
     * @param id the id of the sysEnterpriseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysEnterpriseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-enterprises/{id}")
    public ResponseEntity<SysEnterpriseDTO> getSysEnterprise(@PathVariable Long id) {
        log.debug("REST request to get SysEnterprise : {}", id);
        Optional<SysEnterpriseDTO> sysEnterpriseDTO = sysEnterpriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysEnterpriseDTO);
    }

    /**
     * {@code DELETE  /sys-enterprises/:id} : delete the "id" sysEnterprise.
     *
     * @param id the id of the sysEnterpriseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-enterprises/{id}")
    public ResponseEntity<Void> deleteSysEnterprise(@PathVariable Long id) {
        log.debug("REST request to delete SysEnterprise : {}", id);
        sysEnterpriseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
