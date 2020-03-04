package com.baeldung.newstack.spring;

import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class CustomGrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

    private String superUserDomain = "@baeldung.com";

    public CustomGrantedAuthoritiesExtractor() {
    }

    public CustomGrantedAuthoritiesExtractor(String superUserDomain) {
        super();
        this.superUserDomain = superUserDomain;
    }

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);
        Object username = jwt.getClaims()
            .get("preferred_username");

        if (username instanceof String && ((String) username).endsWith(superUserDomain)) {
            authorities.add(new SimpleGrantedAuthority("SUPERUSER"));
        }
        return authorities;
    }
}