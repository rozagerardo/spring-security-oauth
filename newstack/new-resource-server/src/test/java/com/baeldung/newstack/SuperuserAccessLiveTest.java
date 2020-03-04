package com.baeldung.newstack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.baeldung.newstack.web.dto.ProjectDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SuperuserAccessLiveTest {

    private static final String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
    private static final String AUTHORIZE_URL = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/auth?response_type=code&client_id=newClient&scope=write read&redirect_uri=" + REDIRECT_URL;
    private static final String TOKEN_URL = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/token";
    private static final String PROJECTS_URL = "http://localhost:8081/new-resource-server/api/projects";

    @Test
    public void givenBaeldungUser_whenCreateProject_thenProjectCreated() {
        ProjectDto newProject = new ProjectDto();
        newProject.setName("newProject");
        String accessToken = obtainAccessToken("user@baeldung.com", "pass");

        Response response = RestAssured.given()
            .auth()
            .oauth2(accessToken)
            .contentType(ContentType.JSON)
            .body(newProject)
            .post(PROJECTS_URL);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void givenNonBaeldungUser_whenCreateProject_thenForbidden() {
        ProjectDto newProject = new ProjectDto();
        newProject.setName("newProject");
        String accessToken = obtainAccessToken("john@test.com", "123");

        // Access resources using access token
        Response response = RestAssured.given()
            .auth()
            .oauth2(accessToken)
            .contentType(ContentType.JSON)
            .body(newProject)
            .post(PROJECTS_URL);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private String obtainAccessToken(String username, String password) {
        // obtain authentication url with custom codes
        Response response = RestAssured.given()
            .redirects()
            .follow(false)
            .get(AUTHORIZE_URL);
        String authSessionId = response.getCookie("AUTH_SESSION_ID");
        String kcPostAuthenticationUrl = response.asString()
            .split("action=\"")[1].split("\"")[0].replace("&amp;", "&");

        // obtain authentication code and state
        response = RestAssured.given()
            .redirects()
            .follow(false)
            .cookie("AUTH_SESSION_ID", authSessionId)
            .formParams("username", username, "password", password, "credentialId", "")
            .post(kcPostAuthenticationUrl);
        assertThat(HttpStatus.FOUND.value()).isEqualTo(response.getStatusCode());

        // extract authorization code
        String location = response.getHeader(HttpHeaders.LOCATION);
        String code = location.split("code=")[1].split("&")[0];

        // get access token
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id", "newClient");
        params.put("redirect_uri", REDIRECT_URL);
        params.put("client_secret", "newClientSecret");
        response = RestAssured.given()
            .formParams(params)
            .post(TOKEN_URL);
        return response.jsonPath()
            .getString("access_token");
    }
}
