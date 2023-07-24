import React, {useContext} from 'react';
import {FlatList} from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import {WorkspaceContext} from '../Model/WorkspaceViewModel';

function sortWorkspacesByDate(workspaces) {
  return workspaces
    .slice()
    .sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
}

const WorkspaceList = ({navigation}) => {
  const {workspaces} = useContext(WorkspaceContext);

  const sortedWorkspaces = sortWorkspacesByDate(workspaces);

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
