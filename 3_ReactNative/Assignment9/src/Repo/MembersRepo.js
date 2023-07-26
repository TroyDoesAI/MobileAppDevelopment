// Repo/MembersRepo.js

import {Member} from '../Model/DataClasses';

export const GET_MEMBERS_FOR_WORKSPACE = async (workspaceId, token) => {
  // Debug logs to ensure correct data is passed
  console.log('Workspace ID:', workspaceId);
  console.log('Token:', token);

  const requestUrl = `https://cse118.com/api/v2/workspace/${workspaceId}/member`;
  const requestOptions = {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  };

  // Log the constructed request details
  console.log('Request URL:', requestUrl);
  console.log('Request Options:', JSON.stringify(requestOptions));

  let response;

  try {
    response = await fetch(requestUrl, requestOptions);
  } catch (error) {
    console.error('Fetch failed:', error);
    throw new Error('Failed to initiate fetch request');
  }

  if (!response.ok) {
    const errorData = await response.text();
    console.error('Error data:', errorData);
    throw new Error('Failed to fetch members for the workspace');
  }

  const membersJson = await response.json();
  console.log('Received members data:', membersJson);

  return membersJson.map(
    memberJson => new Member(memberJson.id, memberJson.name),
  );
};

// This is just a test function to execute the above function with sample data
export const testFunction = async () => {
  const sampleWorkspaceId = '750cf325-f2d5-43e1-bdb8-edd4ffe90926';
  const sampleToken =
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijc2M2RiMGRhLTI0Y2EtNDYzMi05NGI5LTVjODkxYzIzZWYxMiIsIm5hbWUiOiJUcm95IFNjaHVsdHoiLCJyb2xlIjoibWVtYmVyIiwiaWF0IjoxNjkwMzMzNTExLCJleHAiOjE2OTA0MTk5MTF9.tXflyn_IhEyadrfUBvF_A5QDidPwH4Xs2d3o8Zkpme4'; // Replace with your token

  try {
    const members = await GET_MEMBERS_FOR_WORKSPACE(
      sampleWorkspaceId,
      sampleToken,
    );
    console.log('\n\nMembers:', members);
  } catch (error) {
    console.error('Test function failed:', error);
  }
};
