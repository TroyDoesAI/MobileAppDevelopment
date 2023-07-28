import React, {useState, useEffect} from 'react';
import {ScrollView} from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import workspacesData from '../Resources/Workspaces.json';

const Assignment8View = ({navigation}) => {
  const [workspaces, setWorkspaces] = useState([]);

  useEffect(() => {
    setWorkspaces(workspacesData);
  }, []);

  return (
    <ScrollView>
      {workspaces.map(workspace => (
        <WorkspaceListItem
          key={workspace.id}
          workspace={workspace}
          navigation={navigation}
        />
      ))}
    </ScrollView>
  );
};

export default Assignment8View;
