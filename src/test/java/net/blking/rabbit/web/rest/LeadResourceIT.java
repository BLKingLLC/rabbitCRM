package net.blking.rabbit.web.rest;

import net.blking.rabbit.RabbitCrmApp;
import net.blking.rabbit.domain.Lead;
import net.blking.rabbit.repository.LeadRepository;
import net.blking.rabbit.web.rest.errors.ExceptionTranslator;

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

import static net.blking.rabbit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LeadResource} REST controller.
 */
@SpringBootTest(classes = RabbitCrmApp.class)
public class LeadResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTACTED = false;
    private static final Boolean UPDATED_CONTACTED = true;

    private static final Boolean DEFAULT_DO_NOT_CONTACT = false;
    private static final Boolean UPDATED_DO_NOT_CONTACT = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private LeadRepository leadRepository;

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

    private MockMvc restLeadMockMvc;

    private Lead lead;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeadResource leadResource = new LeadResource(leadRepository);
        this.restLeadMockMvc = MockMvcBuilders.standaloneSetup(leadResource)
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
    public static Lead createEntity(EntityManager em) {
        Lead lead = new Lead()
            .source(DEFAULT_SOURCE)
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .title(DEFAULT_TITLE)
            .companyName(DEFAULT_COMPANY_NAME)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .county(DEFAULT_COUNTY)
            .city(DEFAULT_CITY)
            .zip(DEFAULT_ZIP)
            .contacted(DEFAULT_CONTACTED)
            .doNotContact(DEFAULT_DO_NOT_CONTACT)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return lead;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lead createUpdatedEntity(EntityManager em) {
        Lead lead = new Lead()
            .source(UPDATED_SOURCE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .title(UPDATED_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .county(UPDATED_COUNTY)
            .city(UPDATED_CITY)
            .zip(UPDATED_ZIP)
            .contacted(UPDATED_CONTACTED)
            .doNotContact(UPDATED_DO_NOT_CONTACT)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return lead;
    }

    @BeforeEach
    public void initTest() {
        lead = createEntity(em);
    }

    @Test
    @Transactional
    public void createLead() throws Exception {
        int databaseSizeBeforeCreate = leadRepository.findAll().size();

        // Create the Lead
        restLeadMockMvc.perform(post("/api/leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lead)))
            .andExpect(status().isCreated());

        // Validate the Lead in the database
        List<Lead> leadList = leadRepository.findAll();
        assertThat(leadList).hasSize(databaseSizeBeforeCreate + 1);
        Lead testLead = leadList.get(leadList.size() - 1);
        assertThat(testLead.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLead.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testLead.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLead.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLead.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testLead.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testLead.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testLead.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testLead.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLead.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testLead.isContacted()).isEqualTo(DEFAULT_CONTACTED);
        assertThat(testLead.isDoNotContact()).isEqualTo(DEFAULT_DO_NOT_CONTACT);
        assertThat(testLead.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLead.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createLeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leadRepository.findAll().size();

        // Create the Lead with an existing ID
        lead.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadMockMvc.perform(post("/api/leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lead)))
            .andExpect(status().isBadRequest());

        // Validate the Lead in the database
        List<Lead> leadList = leadRepository.findAll();
        assertThat(leadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeads() throws Exception {
        // Initialize the database
        leadRepository.saveAndFlush(lead);

        // Get all the leadList
        restLeadMockMvc.perform(get("/api/leads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lead.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)))
            .andExpect(jsonPath("$.[*].contacted").value(hasItem(DEFAULT_CONTACTED.booleanValue())))
            .andExpect(jsonPath("$.[*].doNotContact").value(hasItem(DEFAULT_DO_NOT_CONTACT.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getLead() throws Exception {
        // Initialize the database
        leadRepository.saveAndFlush(lead);

        // Get the lead
        restLeadMockMvc.perform(get("/api/leads/{id}", lead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lead.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP))
            .andExpect(jsonPath("$.contacted").value(DEFAULT_CONTACTED.booleanValue()))
            .andExpect(jsonPath("$.doNotContact").value(DEFAULT_DO_NOT_CONTACT.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingLead() throws Exception {
        // Get the lead
        restLeadMockMvc.perform(get("/api/leads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLead() throws Exception {
        // Initialize the database
        leadRepository.saveAndFlush(lead);

        int databaseSizeBeforeUpdate = leadRepository.findAll().size();

        // Update the lead
        Lead updatedLead = leadRepository.findById(lead.getId()).get();
        // Disconnect from session so that the updates on updatedLead are not directly saved in db
        em.detach(updatedLead);
        updatedLead
            .source(UPDATED_SOURCE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .title(UPDATED_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .county(UPDATED_COUNTY)
            .city(UPDATED_CITY)
            .zip(UPDATED_ZIP)
            .contacted(UPDATED_CONTACTED)
            .doNotContact(UPDATED_DO_NOT_CONTACT)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restLeadMockMvc.perform(put("/api/leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLead)))
            .andExpect(status().isOk());

        // Validate the Lead in the database
        List<Lead> leadList = leadRepository.findAll();
        assertThat(leadList).hasSize(databaseSizeBeforeUpdate);
        Lead testLead = leadList.get(leadList.size() - 1);
        assertThat(testLead.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLead.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testLead.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLead.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLead.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testLead.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testLead.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testLead.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testLead.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLead.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testLead.isContacted()).isEqualTo(UPDATED_CONTACTED);
        assertThat(testLead.isDoNotContact()).isEqualTo(UPDATED_DO_NOT_CONTACT);
        assertThat(testLead.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLead.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingLead() throws Exception {
        int databaseSizeBeforeUpdate = leadRepository.findAll().size();

        // Create the Lead

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadMockMvc.perform(put("/api/leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lead)))
            .andExpect(status().isBadRequest());

        // Validate the Lead in the database
        List<Lead> leadList = leadRepository.findAll();
        assertThat(leadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLead() throws Exception {
        // Initialize the database
        leadRepository.saveAndFlush(lead);

        int databaseSizeBeforeDelete = leadRepository.findAll().size();

        // Delete the lead
        restLeadMockMvc.perform(delete("/api/leads/{id}", lead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lead> leadList = leadRepository.findAll();
        assertThat(leadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
