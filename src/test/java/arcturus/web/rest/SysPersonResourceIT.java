package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysPerson;
import arcturus.repository.SysPersonRepository;
import arcturus.service.SysPersonService;
import arcturus.service.dto.SysPersonDTO;
import arcturus.service.mapper.SysPersonMapper;
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
 * Integration tests for the {@Link SysPersonResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysPersonResourceIT {

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final String DEFAULT_PERSON_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_CONTACT = "BBBBBBBBBB";

    @Autowired
    private SysPersonRepository sysPersonRepository;

    @Autowired
    private SysPersonMapper sysPersonMapper;

    @Autowired
    private SysPersonService sysPersonService;

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

    private MockMvc restSysPersonMockMvc;

    private SysPerson sysPerson;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysPersonResource sysPersonResource = new SysPersonResource(sysPersonService);
        this.restSysPersonMockMvc = MockMvcBuilders.standaloneSetup(sysPersonResource)
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
    public static SysPerson createEntity(EntityManager em) {
        SysPerson sysPerson = new SysPerson()
            .personId(DEFAULT_PERSON_ID)
            .personDescription(DEFAULT_PERSON_DESCRIPTION)
            .personContact(DEFAULT_PERSON_CONTACT);
        return sysPerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysPerson createUpdatedEntity(EntityManager em) {
        SysPerson sysPerson = new SysPerson()
            .personId(UPDATED_PERSON_ID)
            .personDescription(UPDATED_PERSON_DESCRIPTION)
            .personContact(UPDATED_PERSON_CONTACT);
        return sysPerson;
    }

    @BeforeEach
    public void initTest() {
        sysPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysPerson() throws Exception {
        int databaseSizeBeforeCreate = sysPersonRepository.findAll().size();

        // Create the SysPerson
        SysPersonDTO sysPersonDTO = sysPersonMapper.toDto(sysPerson);
        restSysPersonMockMvc.perform(post("/api/sys-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the SysPerson in the database
        List<SysPerson> sysPersonList = sysPersonRepository.findAll();
        assertThat(sysPersonList).hasSize(databaseSizeBeforeCreate + 1);
        SysPerson testSysPerson = sysPersonList.get(sysPersonList.size() - 1);
        assertThat(testSysPerson.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testSysPerson.getPersonDescription()).isEqualTo(DEFAULT_PERSON_DESCRIPTION);
        assertThat(testSysPerson.getPersonContact()).isEqualTo(DEFAULT_PERSON_CONTACT);
    }

    @Test
    @Transactional
    public void createSysPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysPersonRepository.findAll().size();

        // Create the SysPerson with an existing ID
        sysPerson.setId(1L);
        SysPersonDTO sysPersonDTO = sysPersonMapper.toDto(sysPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysPersonMockMvc.perform(post("/api/sys-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysPerson in the database
        List<SysPerson> sysPersonList = sysPersonRepository.findAll();
        assertThat(sysPersonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysPeople() throws Exception {
        // Initialize the database
        sysPersonRepository.saveAndFlush(sysPerson);

        // Get all the sysPersonList
        restSysPersonMockMvc.perform(get("/api/sys-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].personDescription").value(hasItem(DEFAULT_PERSON_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].personContact").value(hasItem(DEFAULT_PERSON_CONTACT.toString())));
    }
    
    @Test
    @Transactional
    public void getSysPerson() throws Exception {
        // Initialize the database
        sysPersonRepository.saveAndFlush(sysPerson);

        // Get the sysPerson
        restSysPersonMockMvc.perform(get("/api/sys-people/{id}", sysPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysPerson.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.personDescription").value(DEFAULT_PERSON_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.personContact").value(DEFAULT_PERSON_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysPerson() throws Exception {
        // Get the sysPerson
        restSysPersonMockMvc.perform(get("/api/sys-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysPerson() throws Exception {
        // Initialize the database
        sysPersonRepository.saveAndFlush(sysPerson);

        int databaseSizeBeforeUpdate = sysPersonRepository.findAll().size();

        // Update the sysPerson
        SysPerson updatedSysPerson = sysPersonRepository.findById(sysPerson.getId()).get();
        // Disconnect from session so that the updates on updatedSysPerson are not directly saved in db
        em.detach(updatedSysPerson);
        updatedSysPerson
            .personId(UPDATED_PERSON_ID)
            .personDescription(UPDATED_PERSON_DESCRIPTION)
            .personContact(UPDATED_PERSON_CONTACT);
        SysPersonDTO sysPersonDTO = sysPersonMapper.toDto(updatedSysPerson);

        restSysPersonMockMvc.perform(put("/api/sys-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonDTO)))
            .andExpect(status().isOk());

        // Validate the SysPerson in the database
        List<SysPerson> sysPersonList = sysPersonRepository.findAll();
        assertThat(sysPersonList).hasSize(databaseSizeBeforeUpdate);
        SysPerson testSysPerson = sysPersonList.get(sysPersonList.size() - 1);
        assertThat(testSysPerson.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testSysPerson.getPersonDescription()).isEqualTo(UPDATED_PERSON_DESCRIPTION);
        assertThat(testSysPerson.getPersonContact()).isEqualTo(UPDATED_PERSON_CONTACT);
    }

    @Test
    @Transactional
    public void updateNonExistingSysPerson() throws Exception {
        int databaseSizeBeforeUpdate = sysPersonRepository.findAll().size();

        // Create the SysPerson
        SysPersonDTO sysPersonDTO = sysPersonMapper.toDto(sysPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysPersonMockMvc.perform(put("/api/sys-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysPerson in the database
        List<SysPerson> sysPersonList = sysPersonRepository.findAll();
        assertThat(sysPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysPerson() throws Exception {
        // Initialize the database
        sysPersonRepository.saveAndFlush(sysPerson);

        int databaseSizeBeforeDelete = sysPersonRepository.findAll().size();

        // Delete the sysPerson
        restSysPersonMockMvc.perform(delete("/api/sys-people/{id}", sysPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysPerson> sysPersonList = sysPersonRepository.findAll();
        assertThat(sysPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysPerson.class);
        SysPerson sysPerson1 = new SysPerson();
        sysPerson1.setId(1L);
        SysPerson sysPerson2 = new SysPerson();
        sysPerson2.setId(sysPerson1.getId());
        assertThat(sysPerson1).isEqualTo(sysPerson2);
        sysPerson2.setId(2L);
        assertThat(sysPerson1).isNotEqualTo(sysPerson2);
        sysPerson1.setId(null);
        assertThat(sysPerson1).isNotEqualTo(sysPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysPersonDTO.class);
        SysPersonDTO sysPersonDTO1 = new SysPersonDTO();
        sysPersonDTO1.setId(1L);
        SysPersonDTO sysPersonDTO2 = new SysPersonDTO();
        assertThat(sysPersonDTO1).isNotEqualTo(sysPersonDTO2);
        sysPersonDTO2.setId(sysPersonDTO1.getId());
        assertThat(sysPersonDTO1).isEqualTo(sysPersonDTO2);
        sysPersonDTO2.setId(2L);
        assertThat(sysPersonDTO1).isNotEqualTo(sysPersonDTO2);
        sysPersonDTO1.setId(null);
        assertThat(sysPersonDTO1).isNotEqualTo(sysPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysPersonMapper.fromId(null)).isNull();
    }
}
