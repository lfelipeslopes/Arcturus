package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysEnterprise;
import arcturus.repository.SysEnterpriseRepository;
import arcturus.service.SysEnterpriseService;
import arcturus.service.dto.SysEnterpriseDTO;
import arcturus.service.mapper.SysEnterpriseMapper;
import arcturus.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static arcturus.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SysEnterpriseResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysEnterpriseResourceIT {

    private static final Long DEFAULT_ENTERPRISE_ID = 1L;
    private static final Long UPDATED_ENTERPRISE_ID = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ENTERPRISE = "AAAAAAAAAA";
    private static final String UPDATED_ENTERPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    @Autowired
    private SysEnterpriseRepository sysEnterpriseRepository;

    @Mock
    private SysEnterpriseRepository sysEnterpriseRepositoryMock;

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    @Mock
    private SysEnterpriseService sysEnterpriseServiceMock;

    @Autowired
    private SysEnterpriseService sysEnterpriseService;

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

    private MockMvc restSysEnterpriseMockMvc;

    private SysEnterprise sysEnterprise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysEnterpriseResource sysEnterpriseResource = new SysEnterpriseResource(sysEnterpriseService);
        this.restSysEnterpriseMockMvc = MockMvcBuilders.standaloneSetup(sysEnterpriseResource)
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
    public static SysEnterprise createEntity(EntityManager em) {
        SysEnterprise sysEnterprise = new SysEnterprise()
            .enterpriseId(DEFAULT_ENTERPRISE_ID)
            .status(DEFAULT_STATUS)
            .enterprise(DEFAULT_ENTERPRISE)
            .alias(DEFAULT_ALIAS);
        return sysEnterprise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysEnterprise createUpdatedEntity(EntityManager em) {
        SysEnterprise sysEnterprise = new SysEnterprise()
            .enterpriseId(UPDATED_ENTERPRISE_ID)
            .status(UPDATED_STATUS)
            .enterprise(UPDATED_ENTERPRISE)
            .alias(UPDATED_ALIAS);
        return sysEnterprise;
    }

    @BeforeEach
    public void initTest() {
        sysEnterprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysEnterprise() throws Exception {
        int databaseSizeBeforeCreate = sysEnterpriseRepository.findAll().size();

        // Create the SysEnterprise
        SysEnterpriseDTO sysEnterpriseDTO = sysEnterpriseMapper.toDto(sysEnterprise);
        restSysEnterpriseMockMvc.perform(post("/api/sys-enterprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysEnterpriseDTO)))
            .andExpect(status().isCreated());

        // Validate the SysEnterprise in the database
        List<SysEnterprise> sysEnterpriseList = sysEnterpriseRepository.findAll();
        assertThat(sysEnterpriseList).hasSize(databaseSizeBeforeCreate + 1);
        SysEnterprise testSysEnterprise = sysEnterpriseList.get(sysEnterpriseList.size() - 1);
        assertThat(testSysEnterprise.getEnterpriseId()).isEqualTo(DEFAULT_ENTERPRISE_ID);
        assertThat(testSysEnterprise.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysEnterprise.getEnterprise()).isEqualTo(DEFAULT_ENTERPRISE);
        assertThat(testSysEnterprise.getAlias()).isEqualTo(DEFAULT_ALIAS);
    }

    @Test
    @Transactional
    public void createSysEnterpriseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysEnterpriseRepository.findAll().size();

        // Create the SysEnterprise with an existing ID
        sysEnterprise.setId(1L);
        SysEnterpriseDTO sysEnterpriseDTO = sysEnterpriseMapper.toDto(sysEnterprise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysEnterpriseMockMvc.perform(post("/api/sys-enterprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysEnterpriseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysEnterprise in the database
        List<SysEnterprise> sysEnterpriseList = sysEnterpriseRepository.findAll();
        assertThat(sysEnterpriseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysEnterprises() throws Exception {
        // Initialize the database
        sysEnterpriseRepository.saveAndFlush(sysEnterprise);

        // Get all the sysEnterpriseList
        restSysEnterpriseMockMvc.perform(get("/api/sys-enterprises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysEnterprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].enterpriseId").value(hasItem(DEFAULT_ENTERPRISE_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].enterprise").value(hasItem(DEFAULT_ENTERPRISE.toString())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSysEnterprisesWithEagerRelationshipsIsEnabled() throws Exception {
        SysEnterpriseResource sysEnterpriseResource = new SysEnterpriseResource(sysEnterpriseServiceMock);
        when(sysEnterpriseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSysEnterpriseMockMvc = MockMvcBuilders.standaloneSetup(sysEnterpriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSysEnterpriseMockMvc.perform(get("/api/sys-enterprises?eagerload=true"))
        .andExpect(status().isOk());

        verify(sysEnterpriseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSysEnterprisesWithEagerRelationshipsIsNotEnabled() throws Exception {
        SysEnterpriseResource sysEnterpriseResource = new SysEnterpriseResource(sysEnterpriseServiceMock);
            when(sysEnterpriseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSysEnterpriseMockMvc = MockMvcBuilders.standaloneSetup(sysEnterpriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSysEnterpriseMockMvc.perform(get("/api/sys-enterprises?eagerload=true"))
        .andExpect(status().isOk());

            verify(sysEnterpriseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSysEnterprise() throws Exception {
        // Initialize the database
        sysEnterpriseRepository.saveAndFlush(sysEnterprise);

        // Get the sysEnterprise
        restSysEnterpriseMockMvc.perform(get("/api/sys-enterprises/{id}", sysEnterprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysEnterprise.getId().intValue()))
            .andExpect(jsonPath("$.enterpriseId").value(DEFAULT_ENTERPRISE_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.enterprise").value(DEFAULT_ENTERPRISE.toString()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysEnterprise() throws Exception {
        // Get the sysEnterprise
        restSysEnterpriseMockMvc.perform(get("/api/sys-enterprises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysEnterprise() throws Exception {
        // Initialize the database
        sysEnterpriseRepository.saveAndFlush(sysEnterprise);

        int databaseSizeBeforeUpdate = sysEnterpriseRepository.findAll().size();

        // Update the sysEnterprise
        SysEnterprise updatedSysEnterprise = sysEnterpriseRepository.findById(sysEnterprise.getId()).get();
        // Disconnect from session so that the updates on updatedSysEnterprise are not directly saved in db
        em.detach(updatedSysEnterprise);
        updatedSysEnterprise
            .enterpriseId(UPDATED_ENTERPRISE_ID)
            .status(UPDATED_STATUS)
            .enterprise(UPDATED_ENTERPRISE)
            .alias(UPDATED_ALIAS);
        SysEnterpriseDTO sysEnterpriseDTO = sysEnterpriseMapper.toDto(updatedSysEnterprise);

        restSysEnterpriseMockMvc.perform(put("/api/sys-enterprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysEnterpriseDTO)))
            .andExpect(status().isOk());

        // Validate the SysEnterprise in the database
        List<SysEnterprise> sysEnterpriseList = sysEnterpriseRepository.findAll();
        assertThat(sysEnterpriseList).hasSize(databaseSizeBeforeUpdate);
        SysEnterprise testSysEnterprise = sysEnterpriseList.get(sysEnterpriseList.size() - 1);
        assertThat(testSysEnterprise.getEnterpriseId()).isEqualTo(UPDATED_ENTERPRISE_ID);
        assertThat(testSysEnterprise.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysEnterprise.getEnterprise()).isEqualTo(UPDATED_ENTERPRISE);
        assertThat(testSysEnterprise.getAlias()).isEqualTo(UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingSysEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = sysEnterpriseRepository.findAll().size();

        // Create the SysEnterprise
        SysEnterpriseDTO sysEnterpriseDTO = sysEnterpriseMapper.toDto(sysEnterprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysEnterpriseMockMvc.perform(put("/api/sys-enterprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysEnterpriseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysEnterprise in the database
        List<SysEnterprise> sysEnterpriseList = sysEnterpriseRepository.findAll();
        assertThat(sysEnterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysEnterprise() throws Exception {
        // Initialize the database
        sysEnterpriseRepository.saveAndFlush(sysEnterprise);

        int databaseSizeBeforeDelete = sysEnterpriseRepository.findAll().size();

        // Delete the sysEnterprise
        restSysEnterpriseMockMvc.perform(delete("/api/sys-enterprises/{id}", sysEnterprise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysEnterprise> sysEnterpriseList = sysEnterpriseRepository.findAll();
        assertThat(sysEnterpriseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysEnterprise.class);
        SysEnterprise sysEnterprise1 = new SysEnterprise();
        sysEnterprise1.setId(1L);
        SysEnterprise sysEnterprise2 = new SysEnterprise();
        sysEnterprise2.setId(sysEnterprise1.getId());
        assertThat(sysEnterprise1).isEqualTo(sysEnterprise2);
        sysEnterprise2.setId(2L);
        assertThat(sysEnterprise1).isNotEqualTo(sysEnterprise2);
        sysEnterprise1.setId(null);
        assertThat(sysEnterprise1).isNotEqualTo(sysEnterprise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysEnterpriseDTO.class);
        SysEnterpriseDTO sysEnterpriseDTO1 = new SysEnterpriseDTO();
        sysEnterpriseDTO1.setId(1L);
        SysEnterpriseDTO sysEnterpriseDTO2 = new SysEnterpriseDTO();
        assertThat(sysEnterpriseDTO1).isNotEqualTo(sysEnterpriseDTO2);
        sysEnterpriseDTO2.setId(sysEnterpriseDTO1.getId());
        assertThat(sysEnterpriseDTO1).isEqualTo(sysEnterpriseDTO2);
        sysEnterpriseDTO2.setId(2L);
        assertThat(sysEnterpriseDTO1).isNotEqualTo(sysEnterpriseDTO2);
        sysEnterpriseDTO1.setId(null);
        assertThat(sysEnterpriseDTO1).isNotEqualTo(sysEnterpriseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysEnterpriseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysEnterpriseMapper.fromId(null)).isNull();
    }
}
