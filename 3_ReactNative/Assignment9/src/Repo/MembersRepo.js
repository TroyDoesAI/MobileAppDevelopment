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

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let allMembers = await response.json();
  return allMembers;
};

const fetchMemberById = async (memberId, accessToken) => {
  try {
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

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    let allMembers = await response.json();
    //console.log('allMembers:', allMembers);

    let member = allMembers.find(m => m.id === memberId);

    if (!member) {
      throw new Error(`Member not found with id: ${memberId}`);
    }

    // Cache the member name for future use.
    membersCache[memberId] = member.name;
    console.log('membersCache[memberId]:', membersCache[memberId]);
    return member.name;
  } catch (error) {
    console.error(`Failed to fetch the member name: ${error}`);
    throw error;
  }
};

export default {fetchMemberById, fetchAllMembers};
