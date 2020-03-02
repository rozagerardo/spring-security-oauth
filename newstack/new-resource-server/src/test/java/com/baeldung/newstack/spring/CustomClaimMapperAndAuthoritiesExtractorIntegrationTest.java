package com.baeldung.newstack.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

public class CustomClaimMapperAndAuthoritiesExtractorIntegrationTest {

    private CustomGrantedAuthoritiesExtractor authoritiesExtractor = new CustomGrantedAuthoritiesExtractor();
    private final MappedJwtClaimSetConverter converter = MappedJwtClaimSetConverter.withDefaults(Collections.singletonMap("preferred_username", new UsernameClaimConverter()));

    @Test
    public void givenJwtWithReadScopeAndBaeldungEmailUsername_whenConverted_thenAuthoritiesWithScopeAndSuperuserAuthority() throws Exception {
        Map<String, Object> originalClaims = Map.of("preferred_username", "user@baeldung.com", "scope", "read");

        Map<String, Object> transformedClaims = converter.convert(originalClaims);
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(claims -> claims.putAll(transformedClaims))
            .build();
        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read", "SUPERUSER");
    }

    @Test
    public void givenJwtWithReadScopeAndOtherEmailUsername_whenConverted_thenAuthoritiesWithScope() throws Exception {
        Map<String, Object> originalClaims = Map.of("preferred_username", "user@other.com", "scope", "read");

        Map<String, Object> transformedClaims = converter.convert(originalClaims);
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(claims -> claims.putAll(transformedClaims))
            .build();
        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read");
    }

    @Test
    public void givenJwtWithReadScope_whenConverted_thenAuthoritiesWithScopeAndNoEmailUsername() throws Exception {
        Map<String, Object> originalClaims = Map.of("preferred_username", "username123", "scope", "read");

        Map<String, Object> transformedClaims = converter.convert(originalClaims);
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(claims -> claims.putAll(transformedClaims))
            .build();
        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .containsExactlyInAnyOrder("SCOPE_read");
    }

    @Test
    public void givenJwtWithNoScope_whenConverted_thenNoAuthoritiesRetrieved() throws Exception {
        Map<String, Object> originalClaims = Map.of("preferred_username", "username123");

        Map<String, Object> transformedClaims = converter.convert(originalClaims);
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(claims -> claims.putAll(transformedClaims))
            .build();
        Collection<GrantedAuthority> authorities = authoritiesExtractor.convert(jwt);

        assertThat(authorities).extracting(authority -> authority.getAuthority())
            .isEmpty();
    }
}
