// Step 0 - Initiate Process
const Step0 = ({nextStepFn, nextStepStarted}) => (
  <div className="step0 step-container">
    <h2>JS OAuth Client using PKCE</h2>
    <div className="summary">Click to start the authentication process, step by step.</div>
    <div className="action">
      <button onClick={nextStepFn} disabled={nextStepStarted}>Start Process!</button>
    </div>
  </div>
)

window.Step0 = Step0;