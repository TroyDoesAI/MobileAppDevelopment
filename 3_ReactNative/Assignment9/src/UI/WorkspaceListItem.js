// UI/WorkspaceListItem.js
import React, {useState, useContext} from 'react';
import {TouchableWithoutFeedback, StyleSheet, Text, View} from 'react-native';
import {ChannelContext} from '../Model/ChannelViewModel';
import AuthContext from '../Model/AuthContext';

const styles = StyleSheet.create({
  // Your styles here
});

const WorkspaceListItem = ({workspace, navigation}) => {
  const [isLoading, setLoading] = useState(false);
  const {loadChannelsForWorkspace} = React.useContext(ChannelContext);
  const {token} = useContext(AuthContext); // <-- Fetch the token from AuthContext

  const handleWorkspaceClick = async () => {
    setLoading(true);
    await loadChannelsForWorkspace(workspace.id, token); // <-- Pass the token here
    navigation.navigate('Channels', {
      workspaceName: workspace.name,
    });
    setLoading(false);
  };

  return (
    <TouchableWithoutFeedback
      onPress={handleWorkspaceClick}
      accessibilityLabel={workspace.name}
      disabled={isLoading} // Prevent additional clicks while loading
    >
      <View style={styles.container}>
        <Text style={styles.item}>{workspace.name}</Text>
        <Text
          style={styles.details}
          accessibilityLabel={`count for ${workspace.name}`}>
          {`Channels: ${workspace.channels}`}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default WorkspaceListItem;
