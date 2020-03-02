package com.baeldung.newstack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ResourceServerLiveTest {

    private final String redirectUrl = "http://localhost:8082/new-client/login/oauth2/code/custom";
    private final String authorizeUrlPattern = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/auth?response_type=code&client_id=newClient&scope=%s&redirect_uri=" + redirectUrl;
    private final String tokenUrl = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/token";
    private final String userInfoResourceUrl = "http://localhost:8081/new-resource-server/user/info";

    @SuppressWarnings("unchecked")
    @Test
    public void givenUserWithReadScope_whenGetProjectResource_thenSuccess() {
        String accessToken = obtainAccessToken("read", "user@baeldung.com", "pass");
        System.out.println("ACCESS TOKEN: " + accessToken);

        // Access resources using access token
        Response response = RestAssured.given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .get(userInfoResourceUrl);
        System.out.println(response.asString());
        Map<String, String> responseBody = response.as(Map.class);
        assertThat(responseBody.get("authorities")).contains("SCOPE_read")
            .contains("SUPERUSER");
        assertThat(responseBody.get("user_name")).contains("username=user")
            .contains("domain=baeldung.com");

    }

    private String obtainAccessToken(String scopes, String username, String password) {
        // obtain authentication url with custom codes
        Response response = RestAssured.given()
            .redirects()
            .follow(false)
            .get(String.format(authorizeUrlPattern, scopes));
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
        params.put("redirect_uri", redirectUrl);
        params.put("client_secret", "newClientSecret");
        response = RestAssured.given()
            .formParams(params)
            .post(tokenUrl);
        return response.jsonPath()
            .getString("access_token");
    }

}
