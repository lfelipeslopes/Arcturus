package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysGroup;
import arcturus.repository.SysGroupRepository;
import arcturus.service.SysGroupService;
import arcturus.service.dto.SysGroupDTO;
import arcturus.service.mapper.SysGroupMapper;
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
 * Integration tests for the {@Link SysGroupResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysGroupResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    @Autowired
    private SysGroupRepository sysGroupRepository;

    @Mock
    private SysGroupRepository sysGroupRepositoryMock;

    @Autowired
    private SysGroupMapper sysGroupMapper;

    @Mock
    private SysGroupService sysGroupServiceMock;

    @Autowired
    private SysGroupService sysGroupService;

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

    private MockMvc restSysGroupMockMvc;

    private SysGroup sysGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysGroupResource sysGroupResource = new SysGroupResource(sysGroupService);
        this.restSysGroupMockMvc = MockMvcBuilders.standaloneSetup(sysGroupResource)
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
    public static SysGroup createEntity(EntityManager em) {
        SysGroup sysGroup = new SysGroup()
            .groupId(DEFAULT_GROUP_ID)
            .status(DEFAULT_STATUS)
            .group(DEFAULT_GROUP);
        return sysGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysGroup createUpdatedEntity(EntityManager em) {
        SysGroup sysGroup = new SysGroup()
            .groupId(UPDATED_GROUP_ID)
            .status(UPDATED_STATUS)
            .group(UPDATED_GROUP);
        return sysGroup;
    }

    @BeforeEach
    public void initTest() {
        sysGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysGroup() throws Exception {
        int databaseSizeBeforeCreate = sysGroupRepository.findAll().size();

        // Create the SysGroup
        SysGroupDTO sysGroupDTO = sysGroupMapper.toDto(sysGroup);
        restSysGroupMockMvc.perform(post("/api/sys-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the SysGroup in the database
        List<SysGroup> sysGroupList = sysGroupRepository.findAll();
        assertThat(sysGroupList).hasSize(databaseSizeBeforeCreate + 1);
        SysGroup testSysGroup = sysGroupList.get(sysGroupList.size() - 1);
        assertThat(testSysGroup.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testSysGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysGroup.getGroup()).isEqualTo(DEFAULT_GROUP);
    }

    @Test
    @Transactional
    public void createSysGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysGroupRepository.findAll().size();

        // Create the SysGroup with an existing ID
        sysGroup.setId(1L);
        SysGroupDTO sysGroupDTO = sysGroupMapper.toDto(sysGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysGroupMockMvc.perform(post("/api/sys-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysGroup in the database
        List<SysGroup> sysGroupList = sysGroupRepository.findAll();
        assertThat(sysGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysGroups() throws Exception {
        // Initialize the database
        sysGroupRepository.saveAndFlush(sysGroup);

        // Get all the sysGroupList
        restSysGroupMockMvc.perform(get("/api/sys-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSysGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        SysGroupResource sysGroupResource = new SysGroupResource(sysGroupServiceMock);
        when(sysGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSysGroupMockMvc = MockMvcBuilders.standaloneSetup(sysGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSysGroupMockMvc.perform(get("/api/sys-groups?eagerload=true"))
        .andExpect(status().isOk());

        verify(sysGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSysGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SysGroupResource sysGroupResource = new SysGroupResource(sysGroupServiceMock);
            when(sysGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSysGroupMockMvc = MockMvcBuilders.standaloneSetup(sysGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSysGroupMockMvc.perform(get("/api/sys-groups?eagerload=true"))
        .andExpect(status().isOk());

            verify(sysGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSysGroup() throws Exception {
        // Initialize the database
        sysGroupRepository.saveAndFlush(sysGroup);

        // Get the sysGroup
        restSysGroupMockMvc.perform(get("/api/sys-groups/{id}", sysGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysGroup() throws Exception {
        // Get the sysGroup
        restSysGroupMockMvc.perform(get("/api/sys-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysGroup() throws Exception {
        // Initialize the database
        sysGroupRepository.saveAndFlush(sysGroup);

        int databaseSizeBeforeUpdate = sysGroupRepository.findAll().size();

        // Update the sysGroup
        SysGroup updatedSysGroup = sysGroupRepository.findById(sysGroup.getId()).get();
        // Disconnect from session so that the updates on updatedSysGroup are not directly saved in db
        em.detach(updatedSysGroup);
        updatedSysGroup
            .groupId(UPDATED_GROUP_ID)
            .status(UPDATED_STATUS)
            .group(UPDATED_GROUP);
        SysGroupDTO sysGroupDTO = sysGroupMapper.toDto(updatedSysGroup);

        restSysGroupMockMvc.perform(put("/api/sys-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysGroupDTO)))
            .andExpect(status().isOk());

        // Validate the SysGroup in the database
        List<SysGroup> sysGroupList = sysGroupRepository.findAll();
        assertThat(sysGroupList).hasSize(databaseSizeBeforeUpdate);
        SysGroup testSysGroup = sysGroupList.get(sysGroupList.size() - 1);
        assertThat(testSysGroup.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testSysGroup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysGroup.getGroup()).isEqualTo(UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingSysGroup() throws Exception {
        int databaseSizeBeforeUpdate = sysGroupRepository.findAll().size();

        // Create the SysGroup
        SysGroupDTO sysGroupDTO = sysGroupMapper.toDto(sysGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysGroupMockMvc.perform(put("/api/sys-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysGroup in the database
        List<SysGroup> sysGroupList = sysGroupRepository.findAll();
        assertThat(sysGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysGroup() throws Exception {
        // Initialize the database
        sysGroupRepository.saveAndFlush(sysGroup);

        int databaseSizeBeforeDelete = sysGroupRepository.findAll().size();

        // Delete the sysGroup
        restSysGroupMockMvc.perform(delete("/api/sys-groups/{id}", sysGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysGroup> sysGroupList = sysGroupRepository.findAll();
        assertThat(sysGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysGroup.class);
        SysGroup sysGroup1 = new SysGroup();
        sysGroup1.setId(1L);
        SysGroup sysGroup2 = new SysGroup();
        sysGroup2.setId(sysGroup1.getId());
        assertThat(sysGroup1).isEqualTo(sysGroup2);
        sysGroup2.setId(2L);
        assertThat(sysGroup1).isNotEqualTo(sysGroup2);
        sysGroup1.setId(null);
        assertThat(sysGroup1).isNotEqualTo(sysGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysGroupDTO.class);
        SysGroupDTO sysGroupDTO1 = new SysGroupDTO();
        sysGroupDTO1.setId(1L);
        SysGroupDTO sysGroupDTO2 = new SysGroupDTO();
        assertThat(sysGroupDTO1).isNotEqualTo(sysGroupDTO2);
        sysGroupDTO2.setId(sysGroupDTO1.getId());
        assertThat(sysGroupDTO1).isEqualTo(sysGroupDTO2);
        sysGroupDTO2.setId(2L);
        assertThat(sysGroupDTO1).isNotEqualTo(sysGroupDTO2);
        sysGroupDTO1.setId(null);
        assertThat(sysGroupDTO1).isNotEqualTo(sysGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysGroupMapper.fromId(null)).isNull();
    }
}
