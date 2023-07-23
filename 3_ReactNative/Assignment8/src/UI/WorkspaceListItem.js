import React from 'react';
import {
  TouchableWithoutFeedback,
  StyleSheet,
  Text,
  View,
} from 'react-native';

import { WorkspaceContext } from '../Model/WorkspaceViewModel';

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
  // Add more styles as necessary
});

const WorkspaceListItem = ({ workspace, navigation }) => {
  const { setWorkspace } = React.useContext(WorkspaceContext);

  return (
    <TouchableWithoutFeedback
      onPress={() => {
        setWorkspace(workspace);
        navigation.navigate('ChannelListView', { title: workspace.name });
      }}>
      <View style={styles.container}>
        {/* You can add an icon or image related to workspace next to its name if you have one */}
        <Text style={styles.item}>{workspace.name}</Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
