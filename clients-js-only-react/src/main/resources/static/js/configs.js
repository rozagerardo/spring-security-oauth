const CONFIGS = {
  /* By the time we created this tutorial, Okta didn't support PKCE for SPA, CORS was not enabled for the Token endpoint,
  therefore we can't be sure if everything will run correctly if you select this option */
  OKTA: {
    authDomain: "dev-620807.okta.com",
    authEndpoint: "/oauth2/default/v1/authorize",
    clientId: "0oa9kx0dvxerroSPU356",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenDomain: "dev-620807.okta.com",
    tokenEndpoint: '/oauth2/default/v1/token',
    scopes: 'openid profile',
    profileDomain: "dev-620807.okta.com",
    profileEndpoint: "/api/v1/users/me",
    profileFields: {
      name: "profile.firstName",
      lastName: "profile.lastName",
      email: "profile.email",
    }
  },
  /* By the time we created this tutorial, Google didn't support PKCE for SPA, CORS was not enabled for the Token endpoint,
  therefore we can't be sure if everything will run correctly if you select this option */
  GOOGLE: {
    authDomain: "accounts.google.com",
    authEndpoint: "/o/oauth2/v2/auth",
    clientId: "246072928137-0cdfigcmkmm0ikeq7k4fan69883hn915.apps.googleusercontent.com",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenDomain: "googleapis.com",
    tokenEndpoint: '/oauth2/v4/token',
    scopes: 'https://www.googleapis.com/auth/plus.login',
    profileDomain: "googleapis.com",
    profileEndpoint: "/plus/v1/people/me",
    profileFields: {
      name: "name.givenName",
      lastName: "name.familyName",
      email: "emails.0.value",
      picture: "image.url"
    }
  },
  AUTH0: {
    authDomain: "bael-jsonly-pkce.auth0.com",
    authEndpoint: "/authorize",
    clientId: "R7L3XpkJrwcGEkuxrUdSpGAA9NgX9ouQ",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenDomain: "bael-jsonly-pkce.auth0.com",
    tokenEndpoint: '/oauth/token',
    scopes: 'openid profile email read:users',
    audience: "https://bael-jsonly-pkce.auth0.com/api/v2/",
    profileDomain: "bael-jsonly-pkce.auth0.com",
    profileEndpoint: "/userinfo",
    profileFields: {
      name: "nickname",
      email: "email",
      picture: "picture"
    }
  }
}

window.CONFIGS = CONFIGS;