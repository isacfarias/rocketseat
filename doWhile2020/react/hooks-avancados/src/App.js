import * as React from "react";
import { UserFallback } from "./components/UserFallback";
import {  UserForm } from "./components/UserForm";
import { UserView } from "./components/UserView";
import { fetchGithubUser } from "./userService";

const REQUEST_STATUS = {
  IDLE:"idle",
  PENDING:"penddig",
  RESOLVED:"resolved",
  REJECTED:"rejected"
};

const useReducer = (state, action) => {
  
  switch (action.type) {
    case '':
      break;

    default:
      break;
  }
};

const UserInfo = ({ userName }) => {

  
  const [state, setState] = React.useState({
    status: userName ? "penddig" : "idle",
    user: null,
    error: null
  });

  const [state, dispatch]  = React.useReducer(reducer, initalState);

  const { status, user, error } = state;

  React.useEffect(() => {
    if (!userName) return;
    
    setState({state: "penddig"});

    return fetchGithubUser(userName).then(
      (userData) => {
        setState({state: "resolved", user:userData});
      },
      (error) => {
        setState({state: "rejected", error:error});
      });

  }, [userName]);


  switch (status) {

    case "idle":
      return "Submit user";

    case "penddig":
      return <UserFallback userName={userName} />;

    case "resolved":
      return <UserView user={user} />;

    case "rejected":
      return (<div>
        There has an error
        <pre style={{ whiteSpace:"normal"}}> {error} </pre>
      </div>);

    default:
      throw Error(`Unhandled status: ${status}`);
  }
};

const UserSection = ({ onSelect, userName }) => (
  <div>
    <div className="flex justify-center ">
      <UserInfo userName={userName} />
    </div>
  </div>
);

const App = () => {
  const [userName, setUserName] = React.useState(null);
  const handleSubmit = (newUserName) => setUserName(newUserName);
  const handleSelect = (newUserName) => setUserName(newUserName);

  return (
    <div>
      <UserForm userName={userName} onSubmit={handleSubmit} />
      <hr />
      <div className="m-4">
        <UserSection onSelect={handleSelect} userName={userName} />
      </div>
    </div>
  );
};

export default App;
