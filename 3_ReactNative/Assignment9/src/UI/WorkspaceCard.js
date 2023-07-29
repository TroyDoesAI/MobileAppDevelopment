// WorkspaceCard.js

import React from 'react';
import {TouchableWithoutFeedback, Text, View} from 'react-native';
import {WorkspaceContext} from '../Model/WorkspaceViewModel';

const WorkspaceCard = ({workspace, navigation}) => {
  const {setWorkspace} = React.useContext(WorkspaceContext);
  return (
    <TouchableWithoutFeedback
      onPress={() => {
        setWorkspace(workspace);
        navigation.navigate('Channels', {title: workspace.name});
      }}>
      <View>
        <Text>{workspace.name}</Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceCard;
