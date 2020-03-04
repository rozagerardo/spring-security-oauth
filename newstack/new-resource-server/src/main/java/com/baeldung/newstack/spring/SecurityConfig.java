package com.baeldung.newstack.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.authorizeRequests()
              .antMatchers(HttpMethod.GET, "/user/info", "/api/projects/**")
                .hasAuthority("SCOPE_read")
              .antMatchers(HttpMethod.POST, "/api/projects")
                .hasAuthority("SUPERUSER")
              .anyRequest()
                .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(customGrantedAuthoritiesExtractor());
    }//@formatter:on
    
    Converter<Jwt, AbstractAuthenticationToken> customGrantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomGrantedAuthoritiesExtractor());
        return jwtAuthenticationConverter;
    }

}