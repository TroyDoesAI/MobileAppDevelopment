// Repo/MessageRepo.js

import {Message, Member} from '../Model/DataClasses';

export const GET_MESSAGES_FOR_CHANNEL = async (channelId, token) => {
  const response = await fetch(
    `https://cse118.com/api/v2/channel/${channelId}/message`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    },
  );

  if (!response.ok) {
    throw new Error('Failed to fetch messages for the channel');
  }

  const messagesJson = await response.json();

  // Convert the JSON data to Message instances.
  return messagesJson.map(messageJson => {
    return new Message(
      messageJson.id,
      messageJson.content,
      messageJson.posted,
      // This assumes the message JSON includes a member object. Adjust as necessary.
      new Member(messageJson.member.id, messageJson.member.name),
    );
  });
};
