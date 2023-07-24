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
        <Text
          style={styles.details}
          accessibilityLabel={`count for ${workspace.name}`}>{`Channels: ${numberOfChannels}`}</Text>
        <Text
          style={styles.details}
          accessibilityLabel={`members active in ${workspace.name}`}>{`Members: ${uniquePosters}`}</Text>
        <Text
          style={styles.details}
          accessibilityLabel={`latest message in ${workspace.name}`}>{`Latest: ${mostRecentMessageTime}`}</Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;

/*
WorkspaceListItem.js: This component represents a single workspace in the workspace list.

For each workspace, it shows the workspace name, the number of channels, the number of unique members, and the time of the latest message.
This component also contains a TouchableWithoutFeedback wrapper that enables navigation to the Channels page when pressed, and loads all the channels associated with the selected workspace.
The WorkspaceListItem component uses the ChannelContext to access the 'loadChannelsForWorkspace' function. It receives the workspace data and navigation object as props.
Key elements of the workspace information are assigned accessibility labels based on the workspace's name to support automated UI testing.
*/
