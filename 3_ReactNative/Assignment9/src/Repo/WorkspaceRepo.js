// WorkspaceRepo.js

// This module acts as a data access layer, abstracting the source of workspace data from the rest of the application.
// Currently, it fetches the data from a local JSON file. However, it's designed in a way that the rest of the application
// remains agnostic about the data source. Whether it's a local file, API call, or even a database, the interface provided
// by this repository remains consistent.

// Points to consider when migrating to an API or other data sources:
// 1. The method of data retrieval will change (e.g., from local JSON import to an API call using axios or fetch).
// 2. Additional error handling will be required to handle API-specific errors, timeouts, etc.
// 3. Once data is fetched, it may need transformation to fit the desired data structures (e.g., instances of Workspace class).

// The rest of the application, such as React components, should remain unaffected by these changes. Components will
// continue to call GET_WORKSPACES() and expect an array of Workspace instances in return.

// This modular approach offers the advantage of layering and separation of concerns. By isolating changes to this module,
// potential disruptions and bugs in other parts of the application are minimized.

// const workspacesJson = require('../Resources/Workspaces.json');

// /**
//  * Fetches the list of workspaces.
//  *
//  * @returns {Array} - A list of workspaces.
//  * @throws Will throw an error if there's an issue parsing the JSON data.
//  */
// export const GET_WORKSPACES = async () => {
//   return workspacesJson;
// };

import {Workspace, Channel, Message} from '../Model/DataClasses';

const workspacesJson = require('../Resources/Workspaces.json');
// Created a helper function convertToWorkspaceInstance that transforms a workspace JSON object into an instance of the Workspace class. It does this recursively for channels and messages.
const convertToWorkspaceInstance = workspaceJson => {
  const channels = workspaceJson.channels.map(channelJson => {
    const messages = channelJson.messages.map(
      messageJson =>
        new Message(
          messageJson.id,
          messageJson.content,
          messageJson.posted,
          messageJson.member,
        ),
    );
    return new Channel(channelJson.id, channelJson.name, messages);
  });
  return new Workspace(workspaceJson.id, workspaceJson.name, channels);
};

export const GET_WORKSPACES = async () => {
  return workspacesJson.map(workspaceJson =>
    convertToWorkspaceInstance(workspaceJson),
  );
};
