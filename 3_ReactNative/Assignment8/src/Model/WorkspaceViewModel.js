import React from 'react';
import {GET_WORKSPACES} from '../Repo/WorkspaceRepo';

export const WorkspaceContext = React.createContext();

export const WorkspaceProvider = props => {
  const [workspaces, setWorkspaces] = React.useState([]);
  const [workspace, setWorkspace] = React.useState();

  React.useEffect(() => {
    const fetchData = async () => {
      const ws = await GET_WORKSPACES();
      setWorkspaces(ws);
    };
    fetchData();
  }, []);

  return (
    <WorkspaceContext.Provider value={{workspaces, workspace, setWorkspace}}>
      {props.children}
    </WorkspaceContext.Provider>
  );
};
