// Repo/ChannelRepo.js
import {Channel} from '../Model/DataClasses';

export const GET_CHANNELS_FOR_WORKSPACE = async (workspaceId, token) => {
  console.log('\nToken in ChannelRepo:', token); // <-- Add this line before the fetch call
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

  // console.log("\nInside ChannelRepo.js");
  // console.log(response.status);
  if (!response.ok) {
    throw new Error('Failed to fetch channels for the workspace');
  }

  const channelsJson = await response.json();

  // Log the channels data to inspect its structure
  console.log('Received channels data:', channelsJson);

  // Create Channel objects from the response JSON
  return Array.isArray(channelsJson)
    ? channelsJson.map(channelJson => {
        return new Channel(
          channelJson.id,
          channelJson.name,
          channelJson.messages,
        ); // initialize with message count
      })
    : [];
};
