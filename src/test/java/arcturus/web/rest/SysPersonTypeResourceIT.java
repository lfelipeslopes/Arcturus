package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysPersonType;
import arcturus.repository.SysPersonTypeRepository;
import arcturus.service.SysPersonTypeService;
import arcturus.service.dto.SysPersonTypeDTO;
import arcturus.service.mapper.SysPersonTypeMapper;
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
 * Integration tests for the {@Link SysPersonTypeResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysPersonTypeResourceIT {

    private static final Long DEFAULT_PERSON_TYPE_ID = 1L;
    private static final Long UPDATED_PERSON_TYPE_ID = 2L;

    private static final String DEFAULT_PERSON_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SysPersonTypeRepository sysPersonTypeRepository;

    @Autowired
    private SysPersonTypeMapper sysPersonTypeMapper;

    @Autowired
    private SysPersonTypeService sysPersonTypeService;

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

    private MockMvc restSysPersonTypeMockMvc;

    private SysPersonType sysPersonType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysPersonTypeResource sysPersonTypeResource = new SysPersonTypeResource(sysPersonTypeService);
        this.restSysPersonTypeMockMvc = MockMvcBuilders.standaloneSetup(sysPersonTypeResource)
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
    public static SysPersonType createEntity(EntityManager em) {
        SysPersonType sysPersonType = new SysPersonType()
            .personTypeId(DEFAULT_PERSON_TYPE_ID)
            .personTypeCode(DEFAULT_PERSON_TYPE_CODE)
            .personTypeDescription(DEFAULT_PERSON_TYPE_DESCRIPTION);
        return sysPersonType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysPersonType createUpdatedEntity(EntityManager em) {
        SysPersonType sysPersonType = new SysPersonType()
            .personTypeId(UPDATED_PERSON_TYPE_ID)
            .personTypeCode(UPDATED_PERSON_TYPE_CODE)
            .personTypeDescription(UPDATED_PERSON_TYPE_DESCRIPTION);
        return sysPersonType;
    }

    @BeforeEach
    public void initTest() {
        sysPersonType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysPersonType() throws Exception {
        int databaseSizeBeforeCreate = sysPersonTypeRepository.findAll().size();

        // Create the SysPersonType
        SysPersonTypeDTO sysPersonTypeDTO = sysPersonTypeMapper.toDto(sysPersonType);
        restSysPersonTypeMockMvc.perform(post("/api/sys-person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SysPersonType in the database
        List<SysPersonType> sysPersonTypeList = sysPersonTypeRepository.findAll();
        assertThat(sysPersonTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SysPersonType testSysPersonType = sysPersonTypeList.get(sysPersonTypeList.size() - 1);
        assertThat(testSysPersonType.getPersonTypeId()).isEqualTo(DEFAULT_PERSON_TYPE_ID);
        assertThat(testSysPersonType.getPersonTypeCode()).isEqualTo(DEFAULT_PERSON_TYPE_CODE);
        assertThat(testSysPersonType.getPersonTypeDescription()).isEqualTo(DEFAULT_PERSON_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSysPersonTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysPersonTypeRepository.findAll().size();

        // Create the SysPersonType with an existing ID
        sysPersonType.setId(1L);
        SysPersonTypeDTO sysPersonTypeDTO = sysPersonTypeMapper.toDto(sysPersonType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysPersonTypeMockMvc.perform(post("/api/sys-person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysPersonType in the database
        List<SysPersonType> sysPersonTypeList = sysPersonTypeRepository.findAll();
        assertThat(sysPersonTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysPersonTypes() throws Exception {
        // Initialize the database
        sysPersonTypeRepository.saveAndFlush(sysPersonType);

        // Get all the sysPersonTypeList
        restSysPersonTypeMockMvc.perform(get("/api/sys-person-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysPersonType.getId().intValue())))
            .andExpect(jsonPath("$.[*].personTypeId").value(hasItem(DEFAULT_PERSON_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].personTypeCode").value(hasItem(DEFAULT_PERSON_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].personTypeDescription").value(hasItem(DEFAULT_PERSON_TYPE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSysPersonType() throws Exception {
        // Initialize the database
        sysPersonTypeRepository.saveAndFlush(sysPersonType);

        // Get the sysPersonType
        restSysPersonTypeMockMvc.perform(get("/api/sys-person-types/{id}", sysPersonType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysPersonType.getId().intValue()))
            .andExpect(jsonPath("$.personTypeId").value(DEFAULT_PERSON_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.personTypeCode").value(DEFAULT_PERSON_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.personTypeDescription").value(DEFAULT_PERSON_TYPE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysPersonType() throws Exception {
        // Get the sysPersonType
        restSysPersonTypeMockMvc.perform(get("/api/sys-person-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysPersonType() throws Exception {
        // Initialize the database
        sysPersonTypeRepository.saveAndFlush(sysPersonType);

        int databaseSizeBeforeUpdate = sysPersonTypeRepository.findAll().size();

        // Update the sysPersonType
        SysPersonType updatedSysPersonType = sysPersonTypeRepository.findById(sysPersonType.getId()).get();
        // Disconnect from session so that the updates on updatedSysPersonType are not directly saved in db
        em.detach(updatedSysPersonType);
        updatedSysPersonType
            .personTypeId(UPDATED_PERSON_TYPE_ID)
            .personTypeCode(UPDATED_PERSON_TYPE_CODE)
            .personTypeDescription(UPDATED_PERSON_TYPE_DESCRIPTION);
        SysPersonTypeDTO sysPersonTypeDTO = sysPersonTypeMapper.toDto(updatedSysPersonType);

        restSysPersonTypeMockMvc.perform(put("/api/sys-person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SysPersonType in the database
        List<SysPersonType> sysPersonTypeList = sysPersonTypeRepository.findAll();
        assertThat(sysPersonTypeList).hasSize(databaseSizeBeforeUpdate);
        SysPersonType testSysPersonType = sysPersonTypeList.get(sysPersonTypeList.size() - 1);
        assertThat(testSysPersonType.getPersonTypeId()).isEqualTo(UPDATED_PERSON_TYPE_ID);
        assertThat(testSysPersonType.getPersonTypeCode()).isEqualTo(UPDATED_PERSON_TYPE_CODE);
        assertThat(testSysPersonType.getPersonTypeDescription()).isEqualTo(UPDATED_PERSON_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSysPersonType() throws Exception {
        int databaseSizeBeforeUpdate = sysPersonTypeRepository.findAll().size();

        // Create the SysPersonType
        SysPersonTypeDTO sysPersonTypeDTO = sysPersonTypeMapper.toDto(sysPersonType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysPersonTypeMockMvc.perform(put("/api/sys-person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysPersonType in the database
        List<SysPersonType> sysPersonTypeList = sysPersonTypeRepository.findAll();
        assertThat(sysPersonTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysPersonType() throws Exception {
        // Initialize the database
        sysPersonTypeRepository.saveAndFlush(sysPersonType);

        int databaseSizeBeforeDelete = sysPersonTypeRepository.findAll().size();

        // Delete the sysPersonType
        restSysPersonTypeMockMvc.perform(delete("/api/sys-person-types/{id}", sysPersonType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysPersonType> sysPersonTypeList = sysPersonTypeRepository.findAll();
        assertThat(sysPersonTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysPersonType.class);
        SysPersonType sysPersonType1 = new SysPersonType();
        sysPersonType1.setId(1L);
        SysPersonType sysPersonType2 = new SysPersonType();
        sysPersonType2.setId(sysPersonType1.getId());
        assertThat(sysPersonType1).isEqualTo(sysPersonType2);
        sysPersonType2.setId(2L);
        assertThat(sysPersonType1).isNotEqualTo(sysPersonType2);
        sysPersonType1.setId(null);
        assertThat(sysPersonType1).isNotEqualTo(sysPersonType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysPersonTypeDTO.class);
        SysPersonTypeDTO sysPersonTypeDTO1 = new SysPersonTypeDTO();
        sysPersonTypeDTO1.setId(1L);
        SysPersonTypeDTO sysPersonTypeDTO2 = new SysPersonTypeDTO();
        assertThat(sysPersonTypeDTO1).isNotEqualTo(sysPersonTypeDTO2);
        sysPersonTypeDTO2.setId(sysPersonTypeDTO1.getId());
        assertThat(sysPersonTypeDTO1).isEqualTo(sysPersonTypeDTO2);
        sysPersonTypeDTO2.setId(2L);
        assertThat(sysPersonTypeDTO1).isNotEqualTo(sysPersonTypeDTO2);
        sysPersonTypeDTO1.setId(null);
        assertThat(sysPersonTypeDTO1).isNotEqualTo(sysPersonTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysPersonTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysPersonTypeMapper.fromId(null)).isNull();
    }
}
