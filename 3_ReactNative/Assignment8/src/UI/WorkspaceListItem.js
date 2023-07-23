// WorkspaceListItem.js

import React from 'react';
import {TouchableWithoutFeedback, StyleSheet, Text, View} from 'react-native';

import {WorkspaceContext} from '../Model/WorkspaceViewModel';
import {ChannelContext} from '../Model/ChannelViewModel';

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
  },
});

const WorkspaceListItem = ({workspace, navigation}) => {
  const {loadChannelsForWorkspace} = React.useContext(ChannelContext);

  return (
    <TouchableWithoutFeedback
      onPress={async () => {
        await loadChannelsForWorkspace(workspace.id);
        navigation.navigate('Channels', {
          workspaceName: workspace.name,
        });
      }}
      accessibilityLabel={workspace.name}>
      <View style={styles.container}>
        <Text style={styles.item}>{workspace.name}</Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
