// ChannelRepo.js

// This utility function is responsible for fetching channels associated
// with a given workspace from the Workspaces.json file.

/**
 * Fetches the channels associated with a specific workspace ID.
 *
 * @param {number} workspaceId - The ID of the workspace to retrieve channels for.
 * @returns {Array} - A list of channels associated with the given workspace,
 * or an empty array if the workspace is not found.
 * @throws Will throw an error if there's an issue parsing the JSON data.
 */

import {Channel, Message, Member} from '../Model/DataClasses'; // Adjust the path as necessary

const workspacesJson = require('../Resources/Workspaces.json');

export const GET_CHANNELS_FOR_WORKSPACE = async workspaceId => {
  const workspace = workspacesJson.find(ws => ws.id === workspaceId);

  if (!workspace) {
    return [];
  }

  const channels = workspace.channels.map(channelData => {
    const messages = channelData.messages.map(messageData => {
      return new Message(
        messageData.id,
        messageData.content,
        messageData.posted,
        new Member(messageData.member.id, messageData.member.name),
      );
    });
    return new Channel(channelData.id, channelData.name, messages);
  });

  return channels;
};

/*
SUMMARY:

The ChannelRepo.js module serves as a data access layer for channels associated
with workspaces. Its primary function is to extract channel data from the local
Workspaces.json file based on a given workspace ID.

The `GET_CHANNELS_FOR_WORKSPACE` function is equipped to receive a workspace ID as its argument,
and in turn, returns an array of channels associated with that specific workspace. If the workspace
is not identifiable in the data, an empty array is dispatched. This module also integrates classes
from the DataClasses.js file to instantiate objects for channels, messages, and members, thus ensuring
data consistency throughout the application.

INTERACTION WITH OTHER FILES:

1. Any components or modules in need of specific workspace channel details will
   import this utility and invoke it using the required workspace ID.
2. The channels array returned (or an empty one if the workspace is undiscovered)
   can then be utilized by the invoking component or module for display, processing, or any other function.
3. Proper handling of this function's results is crucial. This includes scanning for empty arrays
   (signifying an unfound workspace in the data) and controlling potential errors.
*/
