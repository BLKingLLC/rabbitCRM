package net.blking.rabbit.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.blking.rabbit.web.rest.TestUtil;

public class LeadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lead.class);
        Lead lead1 = new Lead();
        lead1.setId(1L);
        Lead lead2 = new Lead();
        lead2.setId(lead1.getId());
        assertThat(lead1).isEqualTo(lead2);
        lead2.setId(2L);
        assertThat(lead1).isNotEqualTo(lead2);
        lead1.setId(null);
        assertThat(lead1).isNotEqualTo(lead2);
    }
}
