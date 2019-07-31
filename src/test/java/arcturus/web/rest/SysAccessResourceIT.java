package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysAccess;
import arcturus.repository.SysAccessRepository;
import arcturus.service.SysAccessService;
import arcturus.service.dto.SysAccessDTO;
import arcturus.service.mapper.SysAccessMapper;
import arcturus.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static arcturus.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SysAccessResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysAccessResourceIT {

    private static final Long DEFAULT_ACCESS_ID = 1L;
    private static final Long UPDATED_ACCESS_ID = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SysAccessRepository sysAccessRepository;

    @Autowired
    private SysAccessMapper sysAccessMapper;

    @Autowired
    private SysAccessService sysAccessService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSysAccessMockMvc;

    private SysAccess sysAccess;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysAccessResource sysAccessResource = new SysAccessResource(sysAccessService);
        this.restSysAccessMockMvc = MockMvcBuilders.standaloneSetup(sysAccessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysAccess createEntity(EntityManager em) {
        SysAccess sysAccess = new SysAccess()
            .accessId(DEFAULT_ACCESS_ID)
            .status(DEFAULT_STATUS)
            .access(DEFAULT_ACCESS)
            .description(DEFAULT_DESCRIPTION);
        return sysAccess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysAccess createUpdatedEntity(EntityManager em) {
        SysAccess sysAccess = new SysAccess()
            .accessId(UPDATED_ACCESS_ID)
            .status(UPDATED_STATUS)
            .access(UPDATED_ACCESS)
            .description(UPDATED_DESCRIPTION);
        return sysAccess;
    }

    @BeforeEach
    public void initTest() {
        sysAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysAccess() throws Exception {
        int databaseSizeBeforeCreate = sysAccessRepository.findAll().size();

        // Create the SysAccess
        SysAccessDTO sysAccessDTO = sysAccessMapper.toDto(sysAccess);
        restSysAccessMockMvc.perform(post("/api/sys-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the SysAccess in the database
        List<SysAccess> sysAccessList = sysAccessRepository.findAll();
        assertThat(sysAccessList).hasSize(databaseSizeBeforeCreate + 1);
        SysAccess testSysAccess = sysAccessList.get(sysAccessList.size() - 1);
        assertThat(testSysAccess.getAccessId()).isEqualTo(DEFAULT_ACCESS_ID);
        assertThat(testSysAccess.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysAccess.getAccess()).isEqualTo(DEFAULT_ACCESS);
        assertThat(testSysAccess.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSysAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysAccessRepository.findAll().size();

        // Create the SysAccess with an existing ID
        sysAccess.setId(1L);
        SysAccessDTO sysAccessDTO = sysAccessMapper.toDto(sysAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysAccessMockMvc.perform(post("/api/sys-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysAccess in the database
        List<SysAccess> sysAccessList = sysAccessRepository.findAll();
        assertThat(sysAccessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysAccesses() throws Exception {
        // Initialize the database
        sysAccessRepository.saveAndFlush(sysAccess);

        // Get all the sysAccessList
        restSysAccessMockMvc.perform(get("/api/sys-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].accessId").value(hasItem(DEFAULT_ACCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].access").value(hasItem(DEFAULT_ACCESS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSysAccess() throws Exception {
        // Initialize the database
        sysAccessRepository.saveAndFlush(sysAccess);

        // Get the sysAccess
        restSysAccessMockMvc.perform(get("/api/sys-accesses/{id}", sysAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysAccess.getId().intValue()))
            .andExpect(jsonPath("$.accessId").value(DEFAULT_ACCESS_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.access").value(DEFAULT_ACCESS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysAccess() throws Exception {
        // Get the sysAccess
        restSysAccessMockMvc.perform(get("/api/sys-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysAccess() throws Exception {
        // Initialize the database
        sysAccessRepository.saveAndFlush(sysAccess);

        int databaseSizeBeforeUpdate = sysAccessRepository.findAll().size();

        // Update the sysAccess
        SysAccess updatedSysAccess = sysAccessRepository.findById(sysAccess.getId()).get();
        // Disconnect from session so that the updates on updatedSysAccess are not directly saved in db
        em.detach(updatedSysAccess);
        updatedSysAccess
            .accessId(UPDATED_ACCESS_ID)
            .status(UPDATED_STATUS)
            .access(UPDATED_ACCESS)
            .description(UPDATED_DESCRIPTION);
        SysAccessDTO sysAccessDTO = sysAccessMapper.toDto(updatedSysAccess);

        restSysAccessMockMvc.perform(put("/api/sys-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysAccessDTO)))
            .andExpect(status().isOk());

        // Validate the SysAccess in the database
        List<SysAccess> sysAccessList = sysAccessRepository.findAll();
        assertThat(sysAccessList).hasSize(databaseSizeBeforeUpdate);
        SysAccess testSysAccess = sysAccessList.get(sysAccessList.size() - 1);
        assertThat(testSysAccess.getAccessId()).isEqualTo(UPDATED_ACCESS_ID);
        assertThat(testSysAccess.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysAccess.getAccess()).isEqualTo(UPDATED_ACCESS);
        assertThat(testSysAccess.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSysAccess() throws Exception {
        int databaseSizeBeforeUpdate = sysAccessRepository.findAll().size();

        // Create the SysAccess
        SysAccessDTO sysAccessDTO = sysAccessMapper.toDto(sysAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysAccessMockMvc.perform(put("/api/sys-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysAccess in the database
        List<SysAccess> sysAccessList = sysAccessRepository.findAll();
        assertThat(sysAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysAccess() throws Exception {
        // Initialize the database
        sysAccessRepository.saveAndFlush(sysAccess);

        int databaseSizeBeforeDelete = sysAccessRepository.findAll().size();

        // Delete the sysAccess
        restSysAccessMockMvc.perform(delete("/api/sys-accesses/{id}", sysAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysAccess> sysAccessList = sysAccessRepository.findAll();
        assertThat(sysAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysAccess.class);
        SysAccess sysAccess1 = new SysAccess();
        sysAccess1.setId(1L);
        SysAccess sysAccess2 = new SysAccess();
        sysAccess2.setId(sysAccess1.getId());
        assertThat(sysAccess1).isEqualTo(sysAccess2);
        sysAccess2.setId(2L);
        assertThat(sysAccess1).isNotEqualTo(sysAccess2);
        sysAccess1.setId(null);
        assertThat(sysAccess1).isNotEqualTo(sysAccess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysAccessDTO.class);
        SysAccessDTO sysAccessDTO1 = new SysAccessDTO();
        sysAccessDTO1.setId(1L);
        SysAccessDTO sysAccessDTO2 = new SysAccessDTO();
        assertThat(sysAccessDTO1).isNotEqualTo(sysAccessDTO2);
        sysAccessDTO2.setId(sysAccessDTO1.getId());
        assertThat(sysAccessDTO1).isEqualTo(sysAccessDTO2);
        sysAccessDTO2.setId(2L);
        assertThat(sysAccessDTO1).isNotEqualTo(sysAccessDTO2);
        sysAccessDTO1.setId(null);
        assertThat(sysAccessDTO1).isNotEqualTo(sysAccessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysAccessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysAccessMapper.fromId(null)).isNull();
    }
}
