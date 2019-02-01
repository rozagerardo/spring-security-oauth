const CONFIGS = {
  OKTA: {
    authDomain: "dev-620807.okta.com",
    authEndpoint: "/oauth2/default/v1/authorize",
    clientId: "0oa9kx0dvxerroSPU356",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenDomain: "dev-620807.okta.com",
    tokenEndpoint: '/oauth2/default/v1/token',
    scopes: 'userinfo.profile'
  },
  GOOGLE: {
    authDomain: "accounts.google.com",
    authEndpoint: "/o/oauth2/v2/auth",
    clientId: "246072928137-0cdfigcmkmm0ikeq7k4fan69883hn915.apps.googleusercontent.com",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenDomain: "googleapis.com",
    tokenEndpoint: '/oauth2/v4/token',
    scopes: 'https://www.googleapis.com/auth/userinfo.profile'
  }
}

window.CONFIGS = CONFIGS;