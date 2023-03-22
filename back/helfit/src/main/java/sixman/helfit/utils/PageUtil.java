package sixman.helfit.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class PageUtil {
    public static PageInfo getPageInfo(Page<?> page) {
        return PageInfo.builder()
                   .page((page.getNumber() + 1))
                   .size(page.getSize())
                   .totalPages(page.getTotalPages())
                   .totalElements(page.getTotalElements())
                   .build();
    }
    
    @Getter
    @Builder
    public static class PageInfo {
        private Integer page;
        private Integer size;
        private Integer totalPages;
        private Long totalElements;
    }
}
