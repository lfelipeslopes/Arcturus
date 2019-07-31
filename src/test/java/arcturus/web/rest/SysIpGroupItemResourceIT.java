package arcturus.web.rest;

import arcturus.ArcturusApp;
import arcturus.domain.SysIpGroupItem;
import arcturus.repository.SysIpGroupItemRepository;
import arcturus.service.SysIpGroupItemService;
import arcturus.service.dto.SysIpGroupItemDTO;
import arcturus.service.mapper.SysIpGroupItemMapper;
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
 * Integration tests for the {@Link SysIpGroupItemResource} REST controller.
 */
@SpringBootTest(classes = ArcturusApp.class)
public class SysIpGroupItemResourceIT {

    private static final Long DEFAULT_IP_GROUP_ITEM_ID = 1L;
    private static final Long UPDATED_IP_GROUP_ITEM_ID = 2L;

    private static final String DEFAULT_INITIAL_IP = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL_IP = "BBBBBBBBBB";

    private static final String DEFAULT_FINAL_IP = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_IP = "BBBBBBBBBB";

    @Autowired
    private SysIpGroupItemRepository sysIpGroupItemRepository;

    @Autowired
    private SysIpGroupItemMapper sysIpGroupItemMapper;

    @Autowired
    private SysIpGroupItemService sysIpGroupItemService;

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

    private MockMvc restSysIpGroupItemMockMvc;

    private SysIpGroupItem sysIpGroupItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysIpGroupItemResource sysIpGroupItemResource = new SysIpGroupItemResource(sysIpGroupItemService);
        this.restSysIpGroupItemMockMvc = MockMvcBuilders.standaloneSetup(sysIpGroupItemResource)
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
    public static SysIpGroupItem createEntity(EntityManager em) {
        SysIpGroupItem sysIpGroupItem = new SysIpGroupItem()
            .ipGroupItemId(DEFAULT_IP_GROUP_ITEM_ID)
            .initialIp(DEFAULT_INITIAL_IP)
            .finalIp(DEFAULT_FINAL_IP);
        return sysIpGroupItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysIpGroupItem createUpdatedEntity(EntityManager em) {
        SysIpGroupItem sysIpGroupItem = new SysIpGroupItem()
            .ipGroupItemId(UPDATED_IP_GROUP_ITEM_ID)
            .initialIp(UPDATED_INITIAL_IP)
            .finalIp(UPDATED_FINAL_IP);
        return sysIpGroupItem;
    }

    @BeforeEach
    public void initTest() {
        sysIpGroupItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysIpGroupItem() throws Exception {
        int databaseSizeBeforeCreate = sysIpGroupItemRepository.findAll().size();

        // Create the SysIpGroupItem
        SysIpGroupItemDTO sysIpGroupItemDTO = sysIpGroupItemMapper.toDto(sysIpGroupItem);
        restSysIpGroupItemMockMvc.perform(post("/api/sys-ip-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SysIpGroupItem in the database
        List<SysIpGroupItem> sysIpGroupItemList = sysIpGroupItemRepository.findAll();
        assertThat(sysIpGroupItemList).hasSize(databaseSizeBeforeCreate + 1);
        SysIpGroupItem testSysIpGroupItem = sysIpGroupItemList.get(sysIpGroupItemList.size() - 1);
        assertThat(testSysIpGroupItem.getIpGroupItemId()).isEqualTo(DEFAULT_IP_GROUP_ITEM_ID);
        assertThat(testSysIpGroupItem.getInitialIp()).isEqualTo(DEFAULT_INITIAL_IP);
        assertThat(testSysIpGroupItem.getFinalIp()).isEqualTo(DEFAULT_FINAL_IP);
    }

    @Test
    @Transactional
    public void createSysIpGroupItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysIpGroupItemRepository.findAll().size();

        // Create the SysIpGroupItem with an existing ID
        sysIpGroupItem.setId(1L);
        SysIpGroupItemDTO sysIpGroupItemDTO = sysIpGroupItemMapper.toDto(sysIpGroupItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysIpGroupItemMockMvc.perform(post("/api/sys-ip-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysIpGroupItem in the database
        List<SysIpGroupItem> sysIpGroupItemList = sysIpGroupItemRepository.findAll();
        assertThat(sysIpGroupItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysIpGroupItems() throws Exception {
        // Initialize the database
        sysIpGroupItemRepository.saveAndFlush(sysIpGroupItem);

        // Get all the sysIpGroupItemList
        restSysIpGroupItemMockMvc.perform(get("/api/sys-ip-group-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysIpGroupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipGroupItemId").value(hasItem(DEFAULT_IP_GROUP_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].initialIp").value(hasItem(DEFAULT_INITIAL_IP.toString())))
            .andExpect(jsonPath("$.[*].finalIp").value(hasItem(DEFAULT_FINAL_IP.toString())));
    }
    
    @Test
    @Transactional
    public void getSysIpGroupItem() throws Exception {
        // Initialize the database
        sysIpGroupItemRepository.saveAndFlush(sysIpGroupItem);

        // Get the sysIpGroupItem
        restSysIpGroupItemMockMvc.perform(get("/api/sys-ip-group-items/{id}", sysIpGroupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysIpGroupItem.getId().intValue()))
            .andExpect(jsonPath("$.ipGroupItemId").value(DEFAULT_IP_GROUP_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.initialIp").value(DEFAULT_INITIAL_IP.toString()))
            .andExpect(jsonPath("$.finalIp").value(DEFAULT_FINAL_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysIpGroupItem() throws Exception {
        // Get the sysIpGroupItem
        restSysIpGroupItemMockMvc.perform(get("/api/sys-ip-group-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysIpGroupItem() throws Exception {
        // Initialize the database
        sysIpGroupItemRepository.saveAndFlush(sysIpGroupItem);

        int databaseSizeBeforeUpdate = sysIpGroupItemRepository.findAll().size();

        // Update the sysIpGroupItem
        SysIpGroupItem updatedSysIpGroupItem = sysIpGroupItemRepository.findById(sysIpGroupItem.getId()).get();
        // Disconnect from session so that the updates on updatedSysIpGroupItem are not directly saved in db
        em.detach(updatedSysIpGroupItem);
        updatedSysIpGroupItem
            .ipGroupItemId(UPDATED_IP_GROUP_ITEM_ID)
            .initialIp(UPDATED_INITIAL_IP)
            .finalIp(UPDATED_FINAL_IP);
        SysIpGroupItemDTO sysIpGroupItemDTO = sysIpGroupItemMapper.toDto(updatedSysIpGroupItem);

        restSysIpGroupItemMockMvc.perform(put("/api/sys-ip-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupItemDTO)))
            .andExpect(status().isOk());

        // Validate the SysIpGroupItem in the database
        List<SysIpGroupItem> sysIpGroupItemList = sysIpGroupItemRepository.findAll();
        assertThat(sysIpGroupItemList).hasSize(databaseSizeBeforeUpdate);
        SysIpGroupItem testSysIpGroupItem = sysIpGroupItemList.get(sysIpGroupItemList.size() - 1);
        assertThat(testSysIpGroupItem.getIpGroupItemId()).isEqualTo(UPDATED_IP_GROUP_ITEM_ID);
        assertThat(testSysIpGroupItem.getInitialIp()).isEqualTo(UPDATED_INITIAL_IP);
        assertThat(testSysIpGroupItem.getFinalIp()).isEqualTo(UPDATED_FINAL_IP);
    }

    @Test
    @Transactional
    public void updateNonExistingSysIpGroupItem() throws Exception {
        int databaseSizeBeforeUpdate = sysIpGroupItemRepository.findAll().size();

        // Create the SysIpGroupItem
        SysIpGroupItemDTO sysIpGroupItemDTO = sysIpGroupItemMapper.toDto(sysIpGroupItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysIpGroupItemMockMvc.perform(put("/api/sys-ip-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysIpGroupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysIpGroupItem in the database
        List<SysIpGroupItem> sysIpGroupItemList = sysIpGroupItemRepository.findAll();
        assertThat(sysIpGroupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysIpGroupItem() throws Exception {
        // Initialize the database
        sysIpGroupItemRepository.saveAndFlush(sysIpGroupItem);

        int databaseSizeBeforeDelete = sysIpGroupItemRepository.findAll().size();

        // Delete the sysIpGroupItem
        restSysIpGroupItemMockMvc.perform(delete("/api/sys-ip-group-items/{id}", sysIpGroupItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysIpGroupItem> sysIpGroupItemList = sysIpGroupItemRepository.findAll();
        assertThat(sysIpGroupItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysIpGroupItem.class);
        SysIpGroupItem sysIpGroupItem1 = new SysIpGroupItem();
        sysIpGroupItem1.setId(1L);
        SysIpGroupItem sysIpGroupItem2 = new SysIpGroupItem();
        sysIpGroupItem2.setId(sysIpGroupItem1.getId());
        assertThat(sysIpGroupItem1).isEqualTo(sysIpGroupItem2);
        sysIpGroupItem2.setId(2L);
        assertThat(sysIpGroupItem1).isNotEqualTo(sysIpGroupItem2);
        sysIpGroupItem1.setId(null);
        assertThat(sysIpGroupItem1).isNotEqualTo(sysIpGroupItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysIpGroupItemDTO.class);
        SysIpGroupItemDTO sysIpGroupItemDTO1 = new SysIpGroupItemDTO();
        sysIpGroupItemDTO1.setId(1L);
        SysIpGroupItemDTO sysIpGroupItemDTO2 = new SysIpGroupItemDTO();
        assertThat(sysIpGroupItemDTO1).isNotEqualTo(sysIpGroupItemDTO2);
        sysIpGroupItemDTO2.setId(sysIpGroupItemDTO1.getId());
        assertThat(sysIpGroupItemDTO1).isEqualTo(sysIpGroupItemDTO2);
        sysIpGroupItemDTO2.setId(2L);
        assertThat(sysIpGroupItemDTO1).isNotEqualTo(sysIpGroupItemDTO2);
        sysIpGroupItemDTO1.setId(null);
        assertThat(sysIpGroupItemDTO1).isNotEqualTo(sysIpGroupItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysIpGroupItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysIpGroupItemMapper.fromId(null)).isNull();
    }
}
