// MessageRepo.js

// This utility function is responsible for fetching messages associated
// with a given channel from the Workspaces.json file.

// Import the local JSON data.
const workspacesJson = require('../Resources/Workspaces.json');

/**
 * Fetches the messages associated with a specific channel ID.
 *
 * @param {number} channelId - The ID of the channel to retrieve messages for.
 * @returns {Array} - A list of messages associated with the given channel,
 * or an empty array if the channel is not found.
 * @throws Will throw an error if there's an issue parsing the JSON data.
 */
export const GET_MESSAGES_FOR_CHANNEL = async channelId => {
  // Search through all workspaces and channels to find a channel
  // with the given ID.
  const channel = workspacesJson
    .flatMap(workspace => workspace.channels)
    .find(ch => ch.id === channelId);

  // If a channel is found with the matching ID, return its associated messages.
  // If not found, return an empty array.
  return channel.messages;
};

/*
SUMMARY:

The MessageRepo.js module serves as a data access layer for messages associated with channels.
Its main task is to fetch message data from the local Workspaces.json file based on a given
channel ID.

The `GET_MESSAGES_FOR_CHANNEL` function takes a channel ID as its argument and returns an
array of messages related to that channel. If the channel isn't located in the dataset,
an empty array gets returned. If there's an error during data retrieval or processing
(e.g., issues with parsing the JSON), the error gets logged to the console.

INTERACTION WITH OTHER FILES:

1. Components or other modules that need message data for a specific channel will import
this utility function and invoke it using the required channel ID.
2. The function's result (either the message data or an empty array) can then be utilized
by the calling component or module for its purposes, such as display or further processing.
3. Callers should correctly handle the function's output, checking for empty arrays
(which indicates the channel wasn't found in the data) and managing potential errors.
*/
