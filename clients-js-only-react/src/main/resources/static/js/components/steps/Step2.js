// // Step 1 - Create Codes
// // const Step2 = ({ codeVerifier, codeChallenge, nextStepFn, nextStepIsDone }) => (
// //   <div className="step2 step-container">
// //     <h2>Step 2 - Login</h2>
// //     <div id="widget-container"></div>
// //     {/*     
// //     <div className="summary">The client must generate a "Code Verifier" per request:</div>
// //     <div className="result"><span>{codeVerifier}</span></div>
// //     <div className="summary">Additionally, it should transform the code using a S256 transfromation method. This is called the "Code Challenge":</div>
// //     <div className="result"><span>{codeChallenge}</span></div>
// //     <div className="action"> */}
// //     {/* <button onClick={nextStepFn} disabled={nextStepIsDone}>Request Auth Code!</button> */}
// //     {/* </div> */}
// //   </div>
// // )

// class Step2 extends React.Component {
//   componentDidMount() {
//     var signIn = new OktaSignIn({ baseUrl: CONFIGS.OKTA.domain });
//     signIn.renderEl({
//       el: '#widget-container'
//     }, function success(res) {
//       if (res.status === 'SUCCESS') {
//         console.log('Do something with this sessionToken', res.session.token);
//       } else {
//         console.log('Something wrong with this sessionToken', res);
//       }
//     });
//   }

//   render() {
//     return (<div className="step2 step-container">
//       <h2>Step 2 - Login</h2>
//       <div id="widget-container"></div>

//     </div>)
//   }
// }


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