import React, {
  useContext,
  useState,
  useEffect,
  useCallback,
  useMemo,
} from 'react';
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
    GET_WORKSPACES(token).then(data => setFetchedWorkspaces(data));
  }, [token]);

  const handleReset = useCallback(async () => {
    await fetch('https://cse118.com/api/v2/reset', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });

    const updatedWorkspaces = await GET_WORKSPACES(token);
    setFetchedWorkspaces(updatedWorkspaces);
  }, [token]);

  const handleSignOut = useCallback(() => {
    signOut();
    navigation.reset({
      index: 0,
      routes: [{name: 'Login'}],
    });
  }, [signOut, navigation]);

  const headerLeft = useMemo(
    () => (
      <Button
        onPress={handleSignOut}
        title="Logout"
        color="#000"
        accessibilityLabel="logout"
      />
    ),
    [handleSignOut],
  );

  const headerRight = useMemo(
    () => (
      <Button
        onPress={handleReset}
        title="Reset"
        color="#000"
        accessibilityLabel="reset"
      />
    ),
    [handleReset],
  );

  React.useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => headerLeft,
      headerRight: () => headerRight,
      headerBackTitleVisible: false,
    });
  }, [navigation, headerLeft, headerRight]);

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
