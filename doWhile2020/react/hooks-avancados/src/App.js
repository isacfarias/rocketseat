import * as React from "react";
import { UserFallback } from "./components/UserFallback";
import { UserForm } from "./components/UserForm";//UserErrorBoundary, 
import { UserView } from "./components/UserView";
import { fetchGithubUser } from "./userService";

const UserInfo = ({ userName }) => {
  const [user, setUser] = React.useState(null);

  React.useEffect(() => {
    if (!userName) return;
    return fetchGithubUser(userName)
              .then((userData) => {
                setUser(userData)
              });

  }, [userName]);

  if (!userName) {
    return "Submit user";
  } else if (!user) {
    return <UserFallback userName={userName} />
  } else {
     return <UserView user={user} />
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
