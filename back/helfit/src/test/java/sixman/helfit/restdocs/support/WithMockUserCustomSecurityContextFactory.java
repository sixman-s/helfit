package sixman.helfit.restdocs.support;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.entity.UserPrincipal;

import java.util.Collections;
import java.util.List;

import static sixman.helfit.domain.user.entity.User.*;

public class WithMockUserCustomSecurityContextFactory implements WithSecurityContextFactory<WithMockUserCustom> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserCustom annotation) {
        User user = new User(
                annotation.id(),
                "tester@testet.com",
                "tester",
                "",
                EmailVerified.Y,
                PersonalInfoAgreement.Y,
                ProviderType.LOCAL,
                RoleType.USER
            );
        user.setUserId(annotation.userId());

        UserPrincipal userPrincipal = new UserPrincipal(
            user,
            Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userPrincipal,
                "NO_PASS"
            );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return context;
    }
}
