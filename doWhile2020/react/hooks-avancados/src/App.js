import * as React from "react";
import { UserFallback } from "./components/UserFallback";
import { UserForm } from "./components/UserForm";
import { UserView } from "./components/UserView";
import { fetchGithubUser } from "./userService";
import { useAsync, REQUEST_STATUS } from "./regras";



const UserInfo = ({ userName }) => {

  const initialRequestStatus = userName
  ? REQUEST_STATUS.PENDING
  : REQUEST_STATUS.IDLE;

  const { status, error, data: user, run } = useAsync({
    status: initialRequestStatus,
  });

  React.useEffect(() => {
    if (!userName) return;
    return run(fetchGithubUser(userName));
  }, [userName, run]);

  switch (status) {
    case REQUEST_STATUS.IDLE:
      return "Submit user";
    case REQUEST_STATUS.PENDING:
      return <UserFallback userName={ userName } />;
    case REQUEST_STATUS.RESOLVED:
      return <UserView user={user} />;
    case REQUEST_STATUS.REJECTED:
      return (<div>
        There has an error
        <pre style={{ whiteSpace:"normal"}}> { error } </pre>
      </div>);
    default:
      throw Error(`Unhandled status: ${ status }`);
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
