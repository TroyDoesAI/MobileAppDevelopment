// Repo/MembersRepo.js

const API_ENDPOINT = 'https://cse118.com/api/v2/member';

let membersCache = {};

const fetchAllMembers = async accessToken => {
  let response = await fetch(API_ENDPOINT, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
  });

  let allMembers = await response.json();
  return allMembers;
};

const fetchMemberById = async (memberId, accessToken) => {
  if (membersCache[memberId]) {
    return membersCache[memberId];
  }

  let response = await fetch(API_ENDPOINT, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
  });

  let allMembers = await response.json();
  let member = allMembers.find(m => m.id === memberId);

  // Cache the member name for future use.
  membersCache[memberId] = member.name;

  return member.name;
};

export default {fetchMemberById, fetchAllMembers};
