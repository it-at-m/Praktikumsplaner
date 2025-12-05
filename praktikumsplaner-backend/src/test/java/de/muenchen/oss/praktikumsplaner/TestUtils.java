package de.muenchen.oss.praktikumsplaner;

import de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class TestUtils {

    public static JwtAuthenticationToken getJwtAuthenticationToken(AuthoritiesEnum role) {
        var jwt = new Jwt(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(1000),
                Collections.singletonMap("typ", "JWT"),
                Collections.singletonMap("email", "test@test.de"));
        return new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
    }
}
