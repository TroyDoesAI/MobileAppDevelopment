// UI/WorkspaceListItem.js

import React, { useState, useEffect } from 'react';
import { TouchableWithoutFeedback, StyleSheet, Text, View } from 'react-native';
import { ChannelContext } from '../Model/ChannelViewModel';
import { formatElapsedTime } from '../Utils/ElapsedTimeFormatter';
import { GET_MEMBERS_FOR_WORKSPACE } from '../Repo/MembersRepo';
import { Workspace, Member, Message } from '../Model/DataClasses';

const styles = StyleSheet.create({

});

const WorkspaceListItem = ({ workspace, navigation }) => {
  const { loadChannelsForWorkspace } = React.useContext(ChannelContext);

  const [members, setMembers] = useState([]);
  const [mostRecentMessageTime, setMostRecentMessageTime] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const fetchedMembers = await GET_MEMBERS_FOR_WORKSPACE(workspace.id); // Remember to pass the required token if needed
        if (!fetchedMembers) {
          throw new Error("Failed to fetch members for the workspace");
        }
        setMembers(fetchedMembers);

        // Using the helper methods from Workspace class
        // This might need a change if you aren't getting the channels from the same source anymore
        const ws = new Workspace(workspace.id, workspace.name, workspace.channels); 
        setMostRecentMessageTime(ws.mostRecentMessage);
      } catch (error) {
        console.error("Failed to fetch data:", error.message);
      }
    };

    fetchData();
  }, [workspace.id]);

  const handleWorkspaceClick = async () => {
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
        <Text style={styles.details} accessibilityLabel={`count for ${workspace.name}`}>
          {`Channels: ${workspace.channels}`}
        </Text>
        <Text style={styles.details} accessibilityLabel={`members active in ${workspace.name}`}>
          {`Members: ${new Set(members.map(m => m.id)).size}`} {/* Assuming that members is an array of Member objects */}
        </Text>
        <Text style={styles.details} accessibilityLabel={`latest message in ${workspace.name}`}>
          {`Latest: ${mostRecentMessageTime ? formatElapsedTime(mostRecentMessageTime) : 'N/A'}`}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
