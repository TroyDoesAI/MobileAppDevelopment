import React from 'react';
import {TouchableWithoutFeedback, StyleSheet, Text, View} from 'react-native';
import {ChannelContext} from '../Model/ChannelViewModel';
import {formatElapsedTime} from '../Utils/ElapsedTimeFormatter'; // Ensure this utility function is available

const styles = StyleSheet.create({
  container: {
    flexDirection: 'column', // Changed to column to stack details vertically
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
    marginBottom: 10, // Adding some bottom margin for separation
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
    marginBottom: 5, // Small margin for some spacing
  },
  details: {
    paddingLeft: 10,
    fontSize: 14,
    color: 'grey',
  },
});

const WorkspaceListItem = ({workspace, navigation}) => {
  const {loadChannelsForWorkspace} = React.useContext(ChannelContext);

  const numberOfChannels = workspace.channels.length;
  const uniquePosters = workspace.uniquePosters;
  const mostRecentMessageTime = formatElapsedTime(
    new Date(workspace.mostRecentMessage),
  );

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
        <Text style={styles.details}>{`Channels: ${numberOfChannels}`}</Text>
        <Text style={styles.details}>{`Members: ${uniquePosters}`}</Text>
        <Text style={styles.details}>{`Latest: ${mostRecentMessageTime}`}</Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
