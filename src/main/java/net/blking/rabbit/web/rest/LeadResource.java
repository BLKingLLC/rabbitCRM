package net.blking.rabbit.web.rest;

import net.blking.rabbit.domain.Lead;
import net.blking.rabbit.repository.LeadRepository;
import net.blking.rabbit.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.blking.rabbit.domain.Lead}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LeadResource {

    private final Logger log = LoggerFactory.getLogger(LeadResource.class);

    private static final String ENTITY_NAME = "lead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeadRepository leadRepository;

    public LeadResource(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    /**
     * {@code POST  /leads} : Create a new lead.
     *
     * @param lead the lead to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lead, or with status {@code 400 (Bad Request)} if the lead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leads")
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) throws URISyntaxException {
        log.debug("REST request to save Lead : {}", lead);
        if (lead.getId() != null) {
            throw new BadRequestAlertException("A new lead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lead result = leadRepository.save(lead);
        return ResponseEntity.created(new URI("/api/leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leads} : Updates an existing lead.
     *
     * @param lead the lead to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lead,
     * or with status {@code 400 (Bad Request)} if the lead is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lead couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leads")
    public ResponseEntity<Lead> updateLead(@RequestBody Lead lead) throws URISyntaxException {
        log.debug("REST request to update Lead : {}", lead);
        if (lead.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lead result = leadRepository.save(lead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lead.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leads} : get all the leads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leads in body.
     */
    @GetMapping("/leads")
    public List<Lead> getAllLeads() {
        log.debug("REST request to get all Leads");
        return leadRepository.findAll();
    }

    /**
     * {@code GET  /leads/:id} : get the "id" lead.
     *
     * @param id the id of the lead to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lead, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leads/{id}")
    public ResponseEntity<Lead> getLead(@PathVariable Long id) {
        log.debug("REST request to get Lead : {}", id);
        Optional<Lead> lead = leadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lead);
    }

    /**
     * {@code DELETE  /leads/:id} : delete the "id" lead.
     *
     * @param id the id of the lead to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leads/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        log.debug("REST request to delete Lead : {}", id);
        leadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
