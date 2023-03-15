package sixman.helfit.domain.statistics.entity;

import lombok.Getter;
import lombok.Setter;
import sixman.helfit.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Stat extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;
}
