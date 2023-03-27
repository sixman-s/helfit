package sixman.helfit.restdocs.enums;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnumDocs {
    // # User
    Map<String,String> userStatus;
    // Map<String, String> personalInfoAgreement;
    // Map<String, String> activate;
    // Map<String, String> emailVerified;

    // # Physical
    // Map<String,String> gender;
}
