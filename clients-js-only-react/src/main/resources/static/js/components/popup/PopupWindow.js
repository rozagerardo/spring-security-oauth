// PopupWindow component:
class PopupWindow extends React.Component {

  state = {
    codeChallenge: '',
    remainingSeconds: 60,
    codeVerifier: '',
    accessToken: '',
    requestedAccessToken: false
  };

  componentDidMount() {
    window.addEventListener('message', this.onMainWindowMessageFn, false);
    this.interval = setInterval(() => {
      const remainingSeconds = this.state.remainingSeconds > 0 ? this.state.remainingSeconds - 1 : 0;
      this.setState({ remainingSeconds })
    }, 1000);
    const authCodeStatus = {
      authCode: this.props.authCode,
      state: this.props.state,
      type: 'authCode'
    }
    window.opener ? window.opener.postMessage(authCodeStatus, "*") : window.alert("This is not a popup window!");
  }

  componentWillUnmount() {
    if (this.interval) clearInterval(this.interval);
  }

  onMainWindowMessageFn = (e) => {
    const codeVerifier = e.data && e.data.codeVerifier;
    if (codeVerifier) this.setState({
      codeVerifier
    })
  }

  requestAccessTokenFn = () => {
    const { tokenDomain, redirectUri, tokenEndpoint } = CONFIGS.GOOGLE;
    const tokenUrl = 'https://' + tokenDomain + tokenEndpoint;
    const tokenRequestBody = {
      grant_type: 'authorization_code',
      redirect_uri: redirectUri,
      code: this.props.authCode,
      code_verifier: this.state.codeVerifier
    }
    axios.post(tokenUrl, tokenRequestBody)
      .then(function (response) {
        console.log("GERGER3333");
        console.log(response);
        const accessToken = response.data.access_token;
        const tokenStatus = {
          type: 'accessToken',
          accessToken
        }
        window.opener ? window.opener.postMessage(tokenStatus, "*") : window.alert("This is not a popup window!");
        clearInterval(this.interval);
        this.setState({
          accessToken
        })
      })
      .catch(function (error) {
        console.error(error);
        window.alert("Error retrieving token!" + error);
      })
    this.setState({
      requestedAccessToken: true
    })
  }

  closeWindow = () => {
    const closeMessage = {
      type: 'closed'
    }
    window.opener ? window.opener.postMessage(closeMessage, "*") : window.alert("This is not a popup window!");
    window.close;
  }


  render() {
    const { authCode } = this.props;
    const { remainingSeconds, accessToken, requestedAccessToken } = this.state;
    return (
      <div className="popup-container step-container">
        <h2>Popup - OAuth 2 Dance</h2>
        <div className="summary">Auth Server retrieved auth code:</div>
        <div className="result"><span>{authCode}</span></div>
        {(!accessToken)
          ? <div>
            <div className="summary">We got {remainingSeconds} seconds before the Auth Code expires...</div>
            <div className="action">
              <button onClick={this.requestAccessTokenFn} disabled={requestedAccessToken}>Request Access Token!</button>
            </div>
          </div>
          : <div>
            <div className="summary">Auth Server retrieved Access Token:</div>
            <div className="result"><span>{accessToken}</span></div>
            <div className="action">
              <button onClick={this.closeWindow}>Return</button>
            </div>
          </div>
        }
      </div>
    )
  }
}

window.PopupWindow = PopupWindow;