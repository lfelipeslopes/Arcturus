package arcturus.web.rest;

import arcturus.service.SysUserService;
import arcturus.web.rest.errors.BadRequestAlertException;
import arcturus.service.dto.SysUserDTO;

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
 * REST controller for managing {@link arcturus.domain.SysUser}.
 */
@RestController
@RequestMapping("/api")
public class SysUserResource {

    private final Logger log = LoggerFactory.getLogger(SysUserResource.class);

    private static final String ENTITY_NAME = "sysUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysUserService sysUserService;

    public SysUserResource(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * {@code POST  /sys-users} : Create a new sysUser.
     *
     * @param sysUserDTO the sysUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysUserDTO, or with status {@code 400 (Bad Request)} if the sysUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-users")
    public ResponseEntity<SysUserDTO> createSysUser(@RequestBody SysUserDTO sysUserDTO) throws URISyntaxException {
        log.debug("REST request to save SysUser : {}", sysUserDTO);
        if (sysUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysUserDTO result = sysUserService.save(sysUserDTO);
        return ResponseEntity.created(new URI("/api/sys-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-users} : Updates an existing sysUser.
     *
     * @param sysUserDTO the sysUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysUserDTO,
     * or with status {@code 400 (Bad Request)} if the sysUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-users")
    public ResponseEntity<SysUserDTO> updateSysUser(@RequestBody SysUserDTO sysUserDTO) throws URISyntaxException {
        log.debug("REST request to update SysUser : {}", sysUserDTO);
        if (sysUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysUserDTO result = sysUserService.save(sysUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-users} : get all the sysUsers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysUsers in body.
     */
    @GetMapping("/sys-users")
    public ResponseEntity<List<SysUserDTO>> getAllSysUsers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of SysUsers");
        Page<SysUserDTO> page;
        if (eagerload) {
            page = sysUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = sysUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-users/:id} : get the "id" sysUser.
     *
     * @param id the id of the sysUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-users/{id}")
    public ResponseEntity<SysUserDTO> getSysUser(@PathVariable Long id) {
        log.debug("REST request to get SysUser : {}", id);
        Optional<SysUserDTO> sysUserDTO = sysUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysUserDTO);
    }

    /**
     * {@code DELETE  /sys-users/:id} : delete the "id" sysUser.
     *
     * @param id the id of the sysUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-users/{id}")
    public ResponseEntity<Void> deleteSysUser(@PathVariable Long id) {
        log.debug("REST request to delete SysUser : {}", id);
        sysUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
