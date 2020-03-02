package com.baeldung.newstack.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomGrantedAuthoritiesExtractorUnitTest {

    private CustomGrantedAuthoritiesExtractor authoritiesExtractor = new CustomGrantedAuthoritiesExtractor();

    @Test
    public void givenJwtWithReadScopeAndBaeldungEmailUsername_whenConverted_thenAuthoritiesWithScopeAndSuperuserAuthority() throws Exception {
        Map<String, String> usernameClaim = Map.of("username", "user", "domain", "baeldung.com");
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("preferred_username", usernameClaim)
            .claim("scope", "read")
            .build();

        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read", "SUPERUSER");
    }

    @Test
    public void givenJwtWithReadScopeAndOtherEmailUsername_whenConverted_thenAuthoritiesWithScope() throws Exception {
        Map<String, String> usernameClaim = Map.of("username", "user", "domain", "other.com");
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("preferred_username", usernameClaim)
            .claim("scope", "read")
            .build();

        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read");
    }

    @Test
    public void givenJwtWithReadScope_whenConverted_thenAuthoritiesWithScopeAndNoEmailUsername() throws Exception {
        Map<String, String> usernameClaim = Map.of("username", "username123");
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("preferred_username", usernameClaim)
            .claim("scope", "read")
            .build();

        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read");
    }

    @Test
    public void givenJwtWithNoScope_whenConverted_thenNoAuthoritiesRetrieved() throws Exception {
        Map<String, String> usernameClaim = Map.of("username", "username123");
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("preferred_username", usernameClaim)
            .build();

        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .isEmpty();
    }
}
