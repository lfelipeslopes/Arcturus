package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysLicense;
import arcturus.repository.SysLicenseRepository;
import arcturus.service.SysLicenseService;
import arcturus.service.dto.SysLicenseDTO;
import arcturus.service.mapper.SysLicenseMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static arcturus.web.rest.TestUtil.sameInstant;
import static arcturus.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SysLicenseResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysLicenseResourceIT {

    private static final Long DEFAULT_LICENSE_ID = 1L;
    private static final Long UPDATED_LICENSE_ID = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_KEY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MAIN_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_KEY = "BBBBBBBBBB";

    @Autowired
    private SysLicenseRepository sysLicenseRepository;

    @Autowired
    private SysLicenseMapper sysLicenseMapper;

    @Autowired
    private SysLicenseService sysLicenseService;

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

    private MockMvc restSysLicenseMockMvc;

    private SysLicense sysLicense;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysLicenseResource sysLicenseResource = new SysLicenseResource(sysLicenseService);
        this.restSysLicenseMockMvc = MockMvcBuilders.standaloneSetup(sysLicenseResource)
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
    public static SysLicense createEntity(EntityManager em) {
        SysLicense sysLicense = new SysLicense()
            .licenseId(DEFAULT_LICENSE_ID)
            .status(DEFAULT_STATUS)
            .licenseKey(DEFAULT_LICENSE_KEY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .mainKey(DEFAULT_MAIN_KEY);
        return sysLicense;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysLicense createUpdatedEntity(EntityManager em) {
        SysLicense sysLicense = new SysLicense()
            .licenseId(UPDATED_LICENSE_ID)
            .status(UPDATED_STATUS)
            .licenseKey(UPDATED_LICENSE_KEY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .mainKey(UPDATED_MAIN_KEY);
        return sysLicense;
    }

    @BeforeEach
    public void initTest() {
        sysLicense = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysLicense() throws Exception {
        int databaseSizeBeforeCreate = sysLicenseRepository.findAll().size();

        // Create the SysLicense
        SysLicenseDTO sysLicenseDTO = sysLicenseMapper.toDto(sysLicense);
        restSysLicenseMockMvc.perform(post("/api/sys-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysLicenseDTO)))
            .andExpect(status().isCreated());

        // Validate the SysLicense in the database
        List<SysLicense> sysLicenseList = sysLicenseRepository.findAll();
        assertThat(sysLicenseList).hasSize(databaseSizeBeforeCreate + 1);
        SysLicense testSysLicense = sysLicenseList.get(sysLicenseList.size() - 1);
        assertThat(testSysLicense.getLicenseId()).isEqualTo(DEFAULT_LICENSE_ID);
        assertThat(testSysLicense.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysLicense.getLicenseKey()).isEqualTo(DEFAULT_LICENSE_KEY);
        assertThat(testSysLicense.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSysLicense.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSysLicense.getMainKey()).isEqualTo(DEFAULT_MAIN_KEY);
    }

    @Test
    @Transactional
    public void createSysLicenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysLicenseRepository.findAll().size();

        // Create the SysLicense with an existing ID
        sysLicense.setId(1L);
        SysLicenseDTO sysLicenseDTO = sysLicenseMapper.toDto(sysLicense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysLicenseMockMvc.perform(post("/api/sys-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysLicense in the database
        List<SysLicense> sysLicenseList = sysLicenseRepository.findAll();
        assertThat(sysLicenseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysLicenses() throws Exception {
        // Initialize the database
        sysLicenseRepository.saveAndFlush(sysLicense);

        // Get all the sysLicenseList
        restSysLicenseMockMvc.perform(get("/api/sys-licenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysLicense.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseId").value(hasItem(DEFAULT_LICENSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].licenseKey").value(hasItem(DEFAULT_LICENSE_KEY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].mainKey").value(hasItem(DEFAULT_MAIN_KEY.toString())));
    }
    
    @Test
    @Transactional
    public void getSysLicense() throws Exception {
        // Initialize the database
        sysLicenseRepository.saveAndFlush(sysLicense);

        // Get the sysLicense
        restSysLicenseMockMvc.perform(get("/api/sys-licenses/{id}", sysLicense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysLicense.getId().intValue()))
            .andExpect(jsonPath("$.licenseId").value(DEFAULT_LICENSE_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.licenseKey").value(DEFAULT_LICENSE_KEY.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.mainKey").value(DEFAULT_MAIN_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysLicense() throws Exception {
        // Get the sysLicense
        restSysLicenseMockMvc.perform(get("/api/sys-licenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysLicense() throws Exception {
        // Initialize the database
        sysLicenseRepository.saveAndFlush(sysLicense);

        int databaseSizeBeforeUpdate = sysLicenseRepository.findAll().size();

        // Update the sysLicense
        SysLicense updatedSysLicense = sysLicenseRepository.findById(sysLicense.getId()).get();
        // Disconnect from session so that the updates on updatedSysLicense are not directly saved in db
        em.detach(updatedSysLicense);
        updatedSysLicense
            .licenseId(UPDATED_LICENSE_ID)
            .status(UPDATED_STATUS)
            .licenseKey(UPDATED_LICENSE_KEY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .mainKey(UPDATED_MAIN_KEY);
        SysLicenseDTO sysLicenseDTO = sysLicenseMapper.toDto(updatedSysLicense);

        restSysLicenseMockMvc.perform(put("/api/sys-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysLicenseDTO)))
            .andExpect(status().isOk());

        // Validate the SysLicense in the database
        List<SysLicense> sysLicenseList = sysLicenseRepository.findAll();
        assertThat(sysLicenseList).hasSize(databaseSizeBeforeUpdate);
        SysLicense testSysLicense = sysLicenseList.get(sysLicenseList.size() - 1);
        assertThat(testSysLicense.getLicenseId()).isEqualTo(UPDATED_LICENSE_ID);
        assertThat(testSysLicense.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysLicense.getLicenseKey()).isEqualTo(UPDATED_LICENSE_KEY);
        assertThat(testSysLicense.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSysLicense.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSysLicense.getMainKey()).isEqualTo(UPDATED_MAIN_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingSysLicense() throws Exception {
        int databaseSizeBeforeUpdate = sysLicenseRepository.findAll().size();

        // Create the SysLicense
        SysLicenseDTO sysLicenseDTO = sysLicenseMapper.toDto(sysLicense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysLicenseMockMvc.perform(put("/api/sys-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysLicense in the database
        List<SysLicense> sysLicenseList = sysLicenseRepository.findAll();
        assertThat(sysLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysLicense() throws Exception {
        // Initialize the database
        sysLicenseRepository.saveAndFlush(sysLicense);

        int databaseSizeBeforeDelete = sysLicenseRepository.findAll().size();

        // Delete the sysLicense
        restSysLicenseMockMvc.perform(delete("/api/sys-licenses/{id}", sysLicense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysLicense> sysLicenseList = sysLicenseRepository.findAll();
        assertThat(sysLicenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysLicense.class);
        SysLicense sysLicense1 = new SysLicense();
        sysLicense1.setId(1L);
        SysLicense sysLicense2 = new SysLicense();
        sysLicense2.setId(sysLicense1.getId());
        assertThat(sysLicense1).isEqualTo(sysLicense2);
        sysLicense2.setId(2L);
        assertThat(sysLicense1).isNotEqualTo(sysLicense2);
        sysLicense1.setId(null);
        assertThat(sysLicense1).isNotEqualTo(sysLicense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysLicenseDTO.class);
        SysLicenseDTO sysLicenseDTO1 = new SysLicenseDTO();
        sysLicenseDTO1.setId(1L);
        SysLicenseDTO sysLicenseDTO2 = new SysLicenseDTO();
        assertThat(sysLicenseDTO1).isNotEqualTo(sysLicenseDTO2);
        sysLicenseDTO2.setId(sysLicenseDTO1.getId());
        assertThat(sysLicenseDTO1).isEqualTo(sysLicenseDTO2);
        sysLicenseDTO2.setId(2L);
        assertThat(sysLicenseDTO1).isNotEqualTo(sysLicenseDTO2);
        sysLicenseDTO1.setId(null);
        assertThat(sysLicenseDTO1).isNotEqualTo(sysLicenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysLicenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysLicenseMapper.fromId(null)).isNull();
    }
}
