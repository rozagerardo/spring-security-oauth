// App component:
class App extends React.Component{
  state = {
    step1: {
      started: false,
      codeVerifier: '',
      codeChallenge: '',
      state: ''
    },
    step2: {
      started: false,
      popup: null,
      authCode: '',
      accessToken: ''
    },
    step3: {
      started: false
    },
    user: null
  };

  executeStep1CreateCodes = () => {
    const state = generate_status();
    const codeVerifier = generate_code_verifier();
    const codeChallenge = generate_code_challenge(codeVerifier);
    this.setState({
      step1: {
        ...this.state.step1,
        started: true,
        codeVerifier,
        codeChallenge,
        state
      }
    })
  }

  executeStep2RequestCode = () => {
    if (!this.state.user) {
      const {authDomain, clientId, redirectUri, authEndpoint, scopes} = CONFIGS.GOOGLE;
      const authorizationUrl = 'https://' + authDomain + authEndpoint 
      + '?client_id=' + clientId
      + "&response_type=code"
      + '&scope=' + scopes
      + '&redirect_uri=' + encodeURI(redirectUri)
      + '&state=' + this.state.step1.state
      + '&code_challenge_method=S256'
      + '&code_challenge=' + this.state.step1.codeChallenge;
      window.addEventListener('message', this.onPopupResponseFn, false);
      var popup = window.open(authorizationUrl, 'external_login_page', 'width=700,height=500,left=200,top=100');
    }
    this.setState({
      step2: {
        ...this.state.step2,
        started: true,
        popup
      }
    })
  }

  executeStep3RequestCode = () => {
    this.setState({
      step3: {
        ...this.state.step3,
        started: true,
      }
    })
  }

  onPopupResponseFn = (e) => {
    const eventType = e.data && e.data.type;
    switch(eventType) {
      case 'authCode':
        if (e.data.state !== this.state.step1.state) {
          window.alert("Retrieved state [" + e.data.state + "] didn't match stored one! Try again");
          break;
        }
        const popupUpdate = {
          codeVerifier: this.state.step1.codeVerifier
        }
        this.state.step2.popup.postMessage(popupUpdate, "*");
        this.setState({
          step2: {
            ...this.state.step2,
            authCode: e.data.authCode
          }
        });
        break;
      case 'accessToken':
        this.setState({
          step2: {
            ...this.state.step2,
            accessToken: e.data.accessToken
          }
        });
        break;
      case 'closed':
      this.setState({
        step2: {
          ...this.state.step2,
          popup: null
        }
      });
      break;
    }
      
  }

  render(){
    const {step1, step2, step3} = {...this.state};
    return (
      <div className="baeldung-container">
        <Step0 nextStepStarted={step1.started} nextStepFn={this.executeStep1CreateCodes} />
        {step1.started && <Step1 {...step1} nextStepStarted={step2.started} nextStepFn={this.executeStep2RequestCode}  />}
        {step2.started && <Step2 {...step2} nextStepStarted={step3.started} />}
      </div>
    )
  }
}

window.App = App;