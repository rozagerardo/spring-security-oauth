package com.baeldung.newstack.spring;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/info", "/api/projects/**").hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/api/projects").hasAuthority("SCOPE_write")
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer().jwt().jwtAuthenticationConverter(grantedAuthoritiesExtractor());
    }// @formatter:on

    @Bean
    public JwtDecoder customDecoder(OAuth2ResourceServerProperties properties) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt()
            .getJwkSetUri())
            .build();

        jwtDecoder.setClaimSetConverter(MappedJwtClaimSetConverter.withDefaults(Collections.singletonMap("preferred_username", new UsernameClaimConverter())));

        return jwtDecoder;
    }

    Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomGrantedAuthoritiesExtractor());
        return jwtAuthenticationConverter;
    }

}