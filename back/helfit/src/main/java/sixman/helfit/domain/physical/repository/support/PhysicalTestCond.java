package sixman.helfit.domain.physical.repository.support;

import lombok.Data;

@Data
public class PhysicalTestCond {
    private Long physicalId;
    private Integer birth;
    private Integer height;
    private Integer weight;
    private Long userId;
}
