// WorkspaceList.js

import React, {useContext, useState, useEffect} from 'react';
import {FlatList} from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import {WorkspaceContext} from '../Model/WorkspaceViewModel';
import AuthContext from '../Model/AuthContext';  // Import AuthContext
import {GET_WORKSPACES} from '../Repo/WorkspaceRepo'; // Import GET_WORKSPACES function

function sortWorkspacesByDate(workspaces) {
  return workspaces
    .slice()
    .sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
}

const WorkspaceList = ({navigation}) => {
  const {token} = useContext(AuthContext); // Get the token from AuthContext
  const {workspaces} = useContext(WorkspaceContext);
  const [fetchedWorkspaces, setFetchedWorkspaces] = useState([]);

  useEffect(() => {
    if (token) {
      GET_WORKSPACES(token)
        .then(data => setFetchedWorkspaces(data))
        .catch(error => {
          console.error("Error fetching workspaces:", error);
        });
    }
  }, [token]);

  const sortedWorkspaces = sortWorkspacesByDate(fetchedWorkspaces.length > 0 ? fetchedWorkspaces : workspaces);

  return (
    <FlatList
      data={sortedWorkspaces}
      keyExtractor={item => item.id.toString()} // Ensure the key is a string
      renderItem={({item}) => (
        <WorkspaceListItem workspace={item} navigation={navigation} />
      )}
      initialNumToRender={20}
    />
  );
};

export default WorkspaceList;
