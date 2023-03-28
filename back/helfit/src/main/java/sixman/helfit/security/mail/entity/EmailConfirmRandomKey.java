package sixman.helfit.security.mail.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@RedisHash(value = "emailRandomKey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfirmRandomKey {
    @Id
    private String id;

    @Indexed
    private String randomKey;

    @TimeToLive
    private Long expiration;

    public static EmailConfirmRandomKey createEmailRandomKey(
        String email,
        String randomKey,
        Long remainingMilliSeconds
    ){
        return EmailConfirmRandomKey.builder()
                   .id(email)
                   .randomKey(randomKey)
                   .expiration(remainingMilliSeconds)
                   .build();
    }
}
