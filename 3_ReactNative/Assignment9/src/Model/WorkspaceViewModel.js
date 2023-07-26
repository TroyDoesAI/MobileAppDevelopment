// Model/WorkspaceViewModel.js

import React, { useContext } from 'react';
import { GET_WORKSPACES } from '../Repo/WorkspaceRepo';
import AuthContext from '../Model/AuthContext';


export const WorkspaceContext = React.createContext();

export const WorkspaceProvider = props => {
  const [workspaces, setWorkspaces] = React.useState([]);
  const [workspace, setWorkspace] = React.useState();
  const { token } = useContext(AuthContext);

  React.useEffect(() => {
    const fetchData = async () => {
      if (token) {
        const ws = await GET_WORKSPACES(token);
        setWorkspaces(ws);
      }
    };
    fetchData();
  }, [token]);

  return (
    <WorkspaceContext.Provider value={{ workspaces, workspace, setWorkspace }}>
      {props.children}
    </WorkspaceContext.Provider>
  );
};