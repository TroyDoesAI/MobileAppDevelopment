// WorkspaceRepo.js

import {Workspace} from '../Model/DataClasses';

export const GET_WORKSPACES = async token => {
  const response = await fetch('https://cse118.com/api/v2/workspace', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });

  const workspacesJson = await response.json();

  return workspacesJson.map(workspaceJson => {
    const channels = workspaceJson.channels;
    return new Workspace(workspaceJson.id, workspaceJson.name, channels);
  });
};
