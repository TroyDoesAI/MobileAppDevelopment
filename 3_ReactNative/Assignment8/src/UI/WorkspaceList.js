import React, {useContext} from 'react';
import {FlatList} from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import {WorkspaceContext} from '../Model/WorkspaceViewModel';

const WorkspaceList = ({navigation}) => {
  const {workspaces} = useContext(WorkspaceContext); // Use the context here

  return (
    <FlatList
      data={workspaces}
      keyExtractor={item => item.id} // Use the 'id' property of each item as the key
      renderItem={({item}) => (
        <WorkspaceListItem workspace={item} navigation={navigation} />
      )}
      initialNumToRender={20} // Ensure the first 20 items are rendered initially
    />
  );
};

export default WorkspaceList;
