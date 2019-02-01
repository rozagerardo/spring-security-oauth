// We used Okta Playground features to create a mocked user (e.g defeated-beetle@example.com/Embarrassed-Narwhal-Annoyed-Magpie-8)

const CONFIGS = {
  OKTA: {
    domain: "dev-620807.okta.com",
    authEndpoint: "/oauth2/default/v1/authorize",
    clientId: "0oa9kx0dvxerroSPU356",
    redirectUri: "http://localhost:8080/popup_code_handler.html",
    tokenEndpoint: '/oauth2/default/v1/token'
  }
}

window.CONFIGS = CONFIGS;