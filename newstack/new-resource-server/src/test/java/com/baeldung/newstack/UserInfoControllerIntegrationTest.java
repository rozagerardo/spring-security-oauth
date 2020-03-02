package com.baeldung.newstack;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = NewResourceServerApp.class)
@AutoConfigureMockMvc
public class UserInfoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNoAuth_whenGetUserInfoResource_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/user/info"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenUserWithReadAndEmailAuthorities_whenGetUserInfoResource_thenSuccess() throws Exception {
        mockMvc.perform(get("/user/info").with(jwt(jwt -> jwt.claim("preferred_username", "user@test.com")).authorities(new SimpleGrantedAuthority("SCOPE_read"), new SimpleGrantedAuthority("SUPERUSER"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.authorities", containsString("SCOPE_read")))
            .andExpect(jsonPath("$.authorities", containsString("SUPERUSER")))
            .andExpect(jsonPath("$.user_name", containsString("user@test.com")));
    }

}
