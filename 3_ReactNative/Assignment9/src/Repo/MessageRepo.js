// Repo/MessageRepo.js

import {Message, Member} from '../Model/DataClasses';
import MemberRepository from '../Repo/MembersRepo';

export const GET_MESSAGES_FOR_CHANNEL = async (channelId, token) => {
  let response = await fetch(
    `https://cse118.com/api/v2/channel/${channelId}/message`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    },
  );

  const messagesJson = await response.json();
  console.log('messagesJson:', messagesJson);

  // get the members of the channel
  let allMembers = await MemberRepository.fetchAllMembers(token);

  // Convert the JSON data to Message instances.
  return messagesJson.map(messageJson => {
    const member = allMembers.find(m => m.id === messageJson.member);
    return new Message(
      messageJson.id,
      messageJson.content,
      messageJson.posted,
      new Member(messageJson.member, member.name),
    );
  });
};
