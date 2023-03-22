package sixman.helfit.domain.physical.repository.condition;

import lombok.Data;

@Data
public class PhysicalCondition {
    private Long physicalId;
    private Integer birth;
    private Integer height;
    private Integer weight;
    private Long userId;
}
