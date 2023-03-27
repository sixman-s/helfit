package sixman.helfit.restdocs.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class EnumDto<T> {
    private T data;

    private EnumDto(T data) {
        this.data = data;
    }

    public static <T> EnumDto<T> of(T data) {
        return new EnumDto<>(data);
    }
}
