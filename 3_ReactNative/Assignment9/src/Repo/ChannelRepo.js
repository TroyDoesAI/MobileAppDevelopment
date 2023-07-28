// Repo/ChannelRepo.js

import {Channel} from '../Model/DataClasses';

export const GET_CHANNELS_FOR_WORKSPACE = async (workspaceId, token) => {
  console.log('\nToken in ChannelRepo:', token);
  const response = await fetch(
    `https://cse118.com/api/v2/workspace/${workspaceId}/channel`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    },
  );

  const channelsJson = await response.json();

  // Directly map the channelsJson to create Channel objects
  return channelsJson.map(
    channelJson =>
      new Channel(channelJson.id, channelJson.name, channelJson.messages),
  );
};
