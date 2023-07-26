// Repo/ChannelRepo.js

import { Channel, Message, Member } from '../Model/DataClasses';

export const GET_CHANNELS_FOR_WORKSPACE = async (workspaceId, token) => {
  const response = await fetch(`https://cse118.com/api/v2/workspace/${workspaceId}/channel`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch channels for the workspace');
  }

  const channelsJson = await response.json();

  // Convert the JSON data to Channel instances.
  return channelsJson.map(channelJson => {
    return new Channel(channelJson.id, channelJson.name);
    // Note: Messages are not populated here. If needed, you can use another call to the /channel/{id}/message endpoint.
  });
};
