package com.baeldung.newstack.spring;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/info", "/api/projects/**").hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/api/projects").hasAuthority("SCOPE_write")
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer().jwt();
    }// @formatter:on

    @Bean
    public JwtDecoder customDecoder(OAuth2ResourceServerProperties properties) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt()
            .getJwkSetUri())
            .build();

        jwtDecoder.setClaimSetConverter(MappedJwtClaimSetConverter.withDefaults(Collections.singletonMap("preferred_username", new UsernameClaimConverter())));

        return jwtDecoder;
    }

}