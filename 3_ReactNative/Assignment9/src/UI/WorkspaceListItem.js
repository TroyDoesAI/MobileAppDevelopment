// UI/WorkspaceListItem.js

import React, { useState, useEffect } from 'react';
import { TouchableWithoutFeedback, StyleSheet, Text, View } from 'react-native';
import { ChannelContext } from '../Model/ChannelViewModel';
import { formatElapsedTime } from '../Utils/ElapsedTimeFormatter';

const styles = StyleSheet.create({
  // ... (styles remain the same)
});

const WorkspaceListItem = ({ workspace, navigation }) => {
  const { loadChannelsForWorkspace } = React.useContext(ChannelContext);

  const [channels, setChannels] = useState([]);

  // Fetch channels when a workspace is selected
  const fetchChannels = async () => {
    try {
      const response = await fetch(`/workspace/${workspace.id}/channel`); // Add your base API URL
      if (response.ok) {
        const data = await response.json();
        setChannels(data);
      } else {
        console.error("Failed to fetch channels");
      }
    } catch (error) {
      console.error("Error fetching channels:", error);
    }
  };

  // When a workspace is clicked
  const handleWorkspaceClick = async () => {
    await fetchChannels();
    await loadChannelsForWorkspace(workspace.id);
    navigation.navigate('Channels', {
      workspaceName: workspace.name,
    });
  };

  return (
    <TouchableWithoutFeedback
      onPress={handleWorkspaceClick}
      accessibilityLabel={workspace.name}
    >
      <View style={styles.container}>
        <Text style={styles.item}>{workspace.name}</Text>
        <Text
          style={styles.details}
          accessibilityLabel={`count for ${workspace.name}`}
        >
          {`Channels: ${channels.length}`}
        </Text>
        {/* You might need to fetch and compute the uniquePosters and mostRecentMessageTime separately */}
        <Text
          style={styles.details}
          accessibilityLabel={`members active in ${workspace.name}`}
        >
          {`Members: TODO`} {/* Replace 'TODO' with your computation for unique posters */}
        </Text>
        <Text
          style={styles.details}
          accessibilityLabel={`latest message in ${workspace.name}`}
        >
          {`Latest: TODO`} {/* Replace 'TODO' with your computation for the most recent message */}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
