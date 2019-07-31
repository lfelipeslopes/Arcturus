package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysModule;
import arcturus.repository.SysModuleRepository;
import arcturus.service.SysModuleService;
import arcturus.service.dto.SysModuleDTO;
import arcturus.service.mapper.SysModuleMapper;
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
 * Integration tests for the {@Link SysModuleResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysModuleResourceIT {

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final String DEFAULT_MODULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SysModuleRepository sysModuleRepository;

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysModuleService sysModuleService;

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

    private MockMvc restSysModuleMockMvc;

    private SysModule sysModule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysModuleResource sysModuleResource = new SysModuleResource(sysModuleService);
        this.restSysModuleMockMvc = MockMvcBuilders.standaloneSetup(sysModuleResource)
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
    public static SysModule createEntity(EntityManager em) {
        SysModule sysModule = new SysModule()
            .moduleId(DEFAULT_MODULE_ID)
            .moduleCode(DEFAULT_MODULE_CODE)
            .moduleDescription(DEFAULT_MODULE_DESCRIPTION);
        return sysModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysModule createUpdatedEntity(EntityManager em) {
        SysModule sysModule = new SysModule()
            .moduleId(UPDATED_MODULE_ID)
            .moduleCode(UPDATED_MODULE_CODE)
            .moduleDescription(UPDATED_MODULE_DESCRIPTION);
        return sysModule;
    }

    @BeforeEach
    public void initTest() {
        sysModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysModule() throws Exception {
        int databaseSizeBeforeCreate = sysModuleRepository.findAll().size();

        // Create the SysModule
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);
        restSysModuleMockMvc.perform(post("/api/sys-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeCreate + 1);
        SysModule testSysModule = sysModuleList.get(sysModuleList.size() - 1);
        assertThat(testSysModule.getModuleId()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testSysModule.getModuleCode()).isEqualTo(DEFAULT_MODULE_CODE);
        assertThat(testSysModule.getModuleDescription()).isEqualTo(DEFAULT_MODULE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSysModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysModuleRepository.findAll().size();

        // Create the SysModule with an existing ID
        sysModule.setId(1L);
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysModuleMockMvc.perform(post("/api/sys-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysModules() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        // Get all the sysModuleList
        restSysModuleMockMvc.perform(get("/api/sys-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].moduleCode").value(hasItem(DEFAULT_MODULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].moduleDescription").value(hasItem(DEFAULT_MODULE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        // Get the sysModule
        restSysModuleMockMvc.perform(get("/api/sys-modules/{id}", sysModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysModule.getId().intValue()))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.moduleCode").value(DEFAULT_MODULE_CODE.toString()))
            .andExpect(jsonPath("$.moduleDescription").value(DEFAULT_MODULE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysModule() throws Exception {
        // Get the sysModule
        restSysModuleMockMvc.perform(get("/api/sys-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        int databaseSizeBeforeUpdate = sysModuleRepository.findAll().size();

        // Update the sysModule
        SysModule updatedSysModule = sysModuleRepository.findById(sysModule.getId()).get();
        // Disconnect from session so that the updates on updatedSysModule are not directly saved in db
        em.detach(updatedSysModule);
        updatedSysModule
            .moduleId(UPDATED_MODULE_ID)
            .moduleCode(UPDATED_MODULE_CODE)
            .moduleDescription(UPDATED_MODULE_DESCRIPTION);
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(updatedSysModule);

        restSysModuleMockMvc.perform(put("/api/sys-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isOk());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeUpdate);
        SysModule testSysModule = sysModuleList.get(sysModuleList.size() - 1);
        assertThat(testSysModule.getModuleId()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testSysModule.getModuleCode()).isEqualTo(UPDATED_MODULE_CODE);
        assertThat(testSysModule.getModuleDescription()).isEqualTo(UPDATED_MODULE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSysModule() throws Exception {
        int databaseSizeBeforeUpdate = sysModuleRepository.findAll().size();

        // Create the SysModule
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysModuleMockMvc.perform(put("/api/sys-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        int databaseSizeBeforeDelete = sysModuleRepository.findAll().size();

        // Delete the sysModule
        restSysModuleMockMvc.perform(delete("/api/sys-modules/{id}", sysModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysModule.class);
        SysModule sysModule1 = new SysModule();
        sysModule1.setId(1L);
        SysModule sysModule2 = new SysModule();
        sysModule2.setId(sysModule1.getId());
        assertThat(sysModule1).isEqualTo(sysModule2);
        sysModule2.setId(2L);
        assertThat(sysModule1).isNotEqualTo(sysModule2);
        sysModule1.setId(null);
        assertThat(sysModule1).isNotEqualTo(sysModule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysModuleDTO.class);
        SysModuleDTO sysModuleDTO1 = new SysModuleDTO();
        sysModuleDTO1.setId(1L);
        SysModuleDTO sysModuleDTO2 = new SysModuleDTO();
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
        sysModuleDTO2.setId(sysModuleDTO1.getId());
        assertThat(sysModuleDTO1).isEqualTo(sysModuleDTO2);
        sysModuleDTO2.setId(2L);
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
        sysModuleDTO1.setId(null);
        assertThat(sysModuleDTO1).isNotEqualTo(sysModuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysModuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysModuleMapper.fromId(null)).isNull();
    }
}
