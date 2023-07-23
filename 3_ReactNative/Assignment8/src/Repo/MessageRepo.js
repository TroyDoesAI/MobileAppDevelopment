// MessageRepo.js

export const GET_MESSAGES_FOR_CHANNEL = async channelId => {
  try {
    const workspacesJson = require('../Resources/Workspaces.json');
    const channel = workspacesJson
      .flatMap(workspace => workspace.channels)
      .find(channel => channel.id === channelId);
    return channel ? channel.messages : [];
  } catch (error) {
    console.error('Error while parsing json:', error);
  }
};
