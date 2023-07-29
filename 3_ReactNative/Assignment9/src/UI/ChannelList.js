// ChannelList.js

import React, {useLayoutEffect, useCallback, useState, useContext} from 'react';
import {FlatList, Text, StyleSheet} from 'react-native';
import ChannelListItem from './ChannelListItem';
import {ChannelContext} from '../Model/ChannelViewModel';
import AuthContext from '../Model/AuthContext';
import {useFocusEffect} from '@react-navigation/native';
import Icon from 'react-native-vector-icons/Ionicons';

// Define HeaderTitle component outside
const HeaderTitle = ({workspaceName}) => (
  <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
    {workspaceName}
  </Text>
);

// Define HeaderIcon component outside
const HeaderIcon = () => (
  <Icon
    name="add-circle"
    size={24}
    color="#000"
    accessibilityLabel="add channel"
  />
);

const ChannelList = ({navigation, route}) => {
  const {workspaceName, workspaceId} = route.params;
  const {loadChannelsForWorkspace} = React.useContext(ChannelContext);
  const {token} = useContext(AuthContext);
  const [channels, setChannels] = useState([]);

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: HeaderTitle.bind(null, {workspaceName}), // Bind workspaceName directly to the HeaderTitle component
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
      headerBackAccessibilityLabel: 'back to workspaces',
      headerRight: HeaderIcon, // Reference the HeaderIcon component directly
    });
  }, [navigation, workspaceName]);

  useFocusEffect(
    useCallback(() => {
      const fetchChannels = async () => {
        const fetchedChannels = await loadChannelsForWorkspace(
          workspaceId,
          token,
        );
        setChannels(fetchedChannels);
      };
      fetchChannels();
    }, [loadChannelsForWorkspace, token, workspaceId]),
  );

  return (
    <FlatList
      data={channels}
      keyExtractor={item => item.id.toString()}
      renderItem={({item}) => (
        <ChannelListItem
          channel={item}
          navigation={navigation}
          workspaceName={workspaceName}
        />
      )}
      initialNumToRender={20}
    />
  );
};

const styles = StyleSheet.create({
  title: {
    fontSize: 10,
    fontWeight: 'bold',
  },
});

export default ChannelList;
