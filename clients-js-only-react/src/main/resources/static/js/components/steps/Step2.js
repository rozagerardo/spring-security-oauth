function registerPopupListener() {
  window.addEventListener('message', function (e) {
    console.log('gergerger');
    console.log(e.data);
    this.setState(
      this.state.step2.user = { name: "gerardoroza" }
    )
  })
}

// Step 2 - Login
const Step2 = ({ accessToken, authCode, nextStepStarted }) => {

  return (<div className="step2 step-container">
    <h2>Step 2 - Login</h2>
    {authCode
      && (<div>
        <div className="summary">Provider retrieved authorization code:</div>
        <div className="result"><span>{authCode}</span></div>
      </div>)
    }
    {accessToken
      && (<div>
        <div className="summary">Used 'Code Verifier' to obtain the Access Token!</div>
        <div className="result"><span>{accessToken}</span></div>
      </div>)
    }
    {!nextStepStarted
      && <Spinner spinnerText='Waiting for Login process...' />
    }
  </div>
  )
};

window.Step2 = Step2;