package net.blking.rabbit.repository;

import net.blking.rabbit.domain.Lead;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

}
