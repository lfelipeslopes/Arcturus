package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysIpGroup;
import arcturus.repository.SysIpGroupRepository;
import arcturus.service.SysIpGroupService;
import arcturus.service.dto.SysIpGroupDTO;
import arcturus.service.mapper.SysIpGroupMapper;
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
 * Integration tests for the {@Link SysIpGroupResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysIpGroupResourceIT {

    private static final Long DEFAULT_IP_GROUP_ID = 1L;
    private static final Long UPDATED_IP_GROUP_ID = 2L;

    @Autowired
    private SysIpGroupRepository sysIpGroupRepository;

    @Autowired
    private SysIpGroupMapper sysIpGroupMapper;

    @Autowired
    private SysIpGroupService sysIpGroupService;

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

    private MockMvc restSysIpGroupMockMvc;

    private SysIpGroup sysIpGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysIpGroupResource sysIpGroupResource = new SysIpGroupResource(sysIpGroupService);
        this.restSysIpGroupMockMvc = MockMvcBuilders.standaloneSetup(sysIpGroupResource)
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
    public static SysIpGroup createEntity(EntityManager em) {
        SysIpGroup sysIpGroup = new SysIpGroup()
            .ipGroupId(DEFAULT_IP_GROUP_ID);
        return sysIpGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysIpGroup createUpdatedEntity(EntityManager em) {
        SysIpGroup sysIpGroup = new SysIpGroup()
            .ipGroupId(UPDATED_IP_GROUP_ID);
        return sysIpGroup;
    }

    @BeforeEach
    public void initTest() {
        sysIpGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysIpGroup() throws Exception {
        int databaseSizeBeforeCreate = sysIpGroupRepository.findAll().size();

        // Create the SysIpGroup
        SysIpGroupDTO sysIpGroupDTO = sysIpGroupMapper.toDto(sysIpGroup);
        restSysIpGroupMockMvc.perform(post("/api/sys-ip-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the SysIpGroup in the database
        List<SysIpGroup> sysIpGroupList = sysIpGroupRepository.findAll();
        assertThat(sysIpGroupList).hasSize(databaseSizeBeforeCreate + 1);
        SysIpGroup testSysIpGroup = sysIpGroupList.get(sysIpGroupList.size() - 1);
        assertThat(testSysIpGroup.getIpGroupId()).isEqualTo(DEFAULT_IP_GROUP_ID);
    }

    @Test
    @Transactional
    public void createSysIpGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysIpGroupRepository.findAll().size();

        // Create the SysIpGroup with an existing ID
        sysIpGroup.setId(1L);
        SysIpGroupDTO sysIpGroupDTO = sysIpGroupMapper.toDto(sysIpGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysIpGroupMockMvc.perform(post("/api/sys-ip-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysIpGroup in the database
        List<SysIpGroup> sysIpGroupList = sysIpGroupRepository.findAll();
        assertThat(sysIpGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysIpGroups() throws Exception {
        // Initialize the database
        sysIpGroupRepository.saveAndFlush(sysIpGroup);

        // Get all the sysIpGroupList
        restSysIpGroupMockMvc.perform(get("/api/sys-ip-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysIpGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipGroupId").value(hasItem(DEFAULT_IP_GROUP_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSysIpGroup() throws Exception {
        // Initialize the database
        sysIpGroupRepository.saveAndFlush(sysIpGroup);

        // Get the sysIpGroup
        restSysIpGroupMockMvc.perform(get("/api/sys-ip-groups/{id}", sysIpGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysIpGroup.getId().intValue()))
            .andExpect(jsonPath("$.ipGroupId").value(DEFAULT_IP_GROUP_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSysIpGroup() throws Exception {
        // Get the sysIpGroup
        restSysIpGroupMockMvc.perform(get("/api/sys-ip-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysIpGroup() throws Exception {
        // Initialize the database
        sysIpGroupRepository.saveAndFlush(sysIpGroup);

        int databaseSizeBeforeUpdate = sysIpGroupRepository.findAll().size();

        // Update the sysIpGroup
        SysIpGroup updatedSysIpGroup = sysIpGroupRepository.findById(sysIpGroup.getId()).get();
        // Disconnect from session so that the updates on updatedSysIpGroup are not directly saved in db
        em.detach(updatedSysIpGroup);
        updatedSysIpGroup
            .ipGroupId(UPDATED_IP_GROUP_ID);
        SysIpGroupDTO sysIpGroupDTO = sysIpGroupMapper.toDto(updatedSysIpGroup);

        restSysIpGroupMockMvc.perform(put("/api/sys-ip-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupDTO)))
            .andExpect(status().isOk());

        // Validate the SysIpGroup in the database
        List<SysIpGroup> sysIpGroupList = sysIpGroupRepository.findAll();
        assertThat(sysIpGroupList).hasSize(databaseSizeBeforeUpdate);
        SysIpGroup testSysIpGroup = sysIpGroupList.get(sysIpGroupList.size() - 1);
        assertThat(testSysIpGroup.getIpGroupId()).isEqualTo(UPDATED_IP_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSysIpGroup() throws Exception {
        int databaseSizeBeforeUpdate = sysIpGroupRepository.findAll().size();

        // Create the SysIpGroup
        SysIpGroupDTO sysIpGroupDTO = sysIpGroupMapper.toDto(sysIpGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysIpGroupMockMvc.perform(put("/api/sys-ip-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysIpGroup in the database
        List<SysIpGroup> sysIpGroupList = sysIpGroupRepository.findAll();
        assertThat(sysIpGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysIpGroup() throws Exception {
        // Initialize the database
        sysIpGroupRepository.saveAndFlush(sysIpGroup);

        int databaseSizeBeforeDelete = sysIpGroupRepository.findAll().size();

        // Delete the sysIpGroup
        restSysIpGroupMockMvc.perform(delete("/api/sys-ip-groups/{id}", sysIpGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysIpGroup> sysIpGroupList = sysIpGroupRepository.findAll();
        assertThat(sysIpGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysIpGroup.class);
        SysIpGroup sysIpGroup1 = new SysIpGroup();
        sysIpGroup1.setId(1L);
        SysIpGroup sysIpGroup2 = new SysIpGroup();
        sysIpGroup2.setId(sysIpGroup1.getId());
        assertThat(sysIpGroup1).isEqualTo(sysIpGroup2);
        sysIpGroup2.setId(2L);
        assertThat(sysIpGroup1).isNotEqualTo(sysIpGroup2);
        sysIpGroup1.setId(null);
        assertThat(sysIpGroup1).isNotEqualTo(sysIpGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysIpGroupDTO.class);
        SysIpGroupDTO sysIpGroupDTO1 = new SysIpGroupDTO();
        sysIpGroupDTO1.setId(1L);
        SysIpGroupDTO sysIpGroupDTO2 = new SysIpGroupDTO();
        assertThat(sysIpGroupDTO1).isNotEqualTo(sysIpGroupDTO2);
        sysIpGroupDTO2.setId(sysIpGroupDTO1.getId());
        assertThat(sysIpGroupDTO1).isEqualTo(sysIpGroupDTO2);
        sysIpGroupDTO2.setId(2L);
        assertThat(sysIpGroupDTO1).isNotEqualTo(sysIpGroupDTO2);
        sysIpGroupDTO1.setId(null);
        assertThat(sysIpGroupDTO1).isNotEqualTo(sysIpGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysIpGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysIpGroupMapper.fromId(null)).isNull();
    }
}
