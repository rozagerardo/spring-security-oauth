function generate_code_verifier() {
  return random_string(48);
}

function random_string(len) {
  var arr = new Uint8Array(len);
  window.crypto.getRandomValues(arr);
  var str = base64_urlencode(dec2bin(arr));
  return str.substring(0, len);
}

function dec2hex(dec) {
  return ('0' + dec.toString(16)).substr(-2)
}

function dec2bin(arr) {
  return hex2bin(Array.from(arr, dec2hex).join(''));
}

