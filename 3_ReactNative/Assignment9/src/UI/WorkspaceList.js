// WorkspaceList.js
import React, {useContext, useState, useEffect} from 'react';
import {FlatList, Button} from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import {WorkspaceContext} from '../Model/WorkspaceViewModel';
import AuthContext from '../Model/AuthContext';
import {GET_WORKSPACES} from '../Repo/WorkspaceRepo';

function sortWorkspacesByDate(workspaces) {
  return workspaces
    .slice()
    .sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
}

const WorkspaceList = ({navigation}) => {
  const {token, signOut} = useContext(AuthContext);
  const {workspaces} = useContext(WorkspaceContext);
  const [fetchedWorkspaces, setFetchedWorkspaces] = useState([]);

  useEffect(() => {
    if (token) {
      GET_WORKSPACES(token).then(data => setFetchedWorkspaces(data));
      // Removed the .catch() block
    }
  }, [token]);

  // Custom header with logout button
  React.useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        <Button
          onPress={() => {
            signOut();
            navigation.reset({
              index: 0,
              routes: [{name: 'Login'}],
            });
          }}
          title="Logout"
          color="#000"
          accessibilityLabel="logout"
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
      renderItem={({item}) => (
        <WorkspaceListItem workspace={item} navigation={navigation} />
      )}
      initialNumToRender={20}
    />
  );
};

export default WorkspaceList;
