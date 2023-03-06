package sixman.helfit.restdocs.support;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;

public class WithMockUserCustomSecurityContextFactory implements WithSecurityContextFactory<WithMockUserCustom> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserCustom annotation) {
        // User user = new User(annotation.userId(), annotation.username(), "test@test.com", "password", "", List.of(annotation.role()), new Reputation(), true);
        // PrincipalDetails principal = new PrincipalDetails(user);
        //
        // UsernamePasswordAuthenticationToken authentication =
        //     new UsernamePasswordAuthenticationToken(
        //         principal,
        //         "password",
        //         List.of(new SimpleGrantedAuthority(annotation.role())
        //         )
        //     );

        SecurityContext context = SecurityContextHolder.getContext();
        // context.setAuthentication(authentication);

        return context;
    }
}
