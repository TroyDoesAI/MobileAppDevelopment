import { Member } from '../Model/DataClasses';

export const GET_MEMBERS_FOR_WORKSPACE = async (workspaceId, token) => {
  const response = await fetch(`https://cse118.com/api/v2/workspace/${workspaceId}/member`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch members for the workspace');
  }

  const membersJson = await response.json();
  return membersJson.map(memberJson => new Member(memberJson.id, memberJson.name));
};
