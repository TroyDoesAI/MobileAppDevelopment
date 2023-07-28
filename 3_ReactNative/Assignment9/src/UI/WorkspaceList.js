// WorkspaceList.js

import React, { useContext, useState, useEffect } from 'react';
import { FlatList, Button } from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import { WorkspaceContext } from '../Model/WorkspaceViewModel';
import AuthContext from '../Model/AuthContext';
import { GET_WORKSPACES } from '../Repo/WorkspaceRepo';

function sortWorkspacesByDate(workspaces) {
  return workspaces
    .slice()
    .sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
}

const WorkspaceList = ({ navigation }) => {
  const { token, signOut } = useContext(AuthContext);
  const { workspaces } = useContext(WorkspaceContext);
  const [fetchedWorkspaces, setFetchedWorkspaces] = useState([]);

  useEffect(() => {
    if (token) {
      GET_WORKSPACES(token).then(data => setFetchedWorkspaces(data));
    }
  }, [token]);

  const handleReset = async () => {
    await fetch('https://cse118.com/api/v2/reset', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  };

  // Custom header with logout and reset button
  React.useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        <Button
          onPress={() => {
            signOut();
            navigation.reset({
              index: 0,
              routes: [{ name: 'Login' }],
            });
          }}
          title="Logout"
          color="#000"
          accessibilityLabel="logout"
        />
      ),
      headerRight: () => (
        <Button
          onPress={handleReset}
          title="Reset"
          color="#000"
          accessibilityLabel="reset"
        />
      ),
      headerBackTitleVisible: false,
    });
  }, [navigation, signOut]);

  const sortedWorkspaces = sortWorkspacesByDate(
    fetchedWorkspaces.length > 0 ? fetchedWorkspaces : workspaces,
  );

  return (
    <FlatList
      data={sortedWorkspaces}
      keyExtractor={item => item.id.toString()}
      renderItem={({ item }) => (
        <WorkspaceListItem workspace={item} navigation={navigation} />
      )}
      initialNumToRender={20}
    />
  );
};

export default WorkspaceList;
