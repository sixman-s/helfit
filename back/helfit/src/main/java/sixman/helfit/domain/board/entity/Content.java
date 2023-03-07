package sixman.helfit.domain.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
public class Content {
    @Column(nullable = false, length = 20000)
    private String text;
    @Column(nullable = false)
    private MultipartFile image;
}
