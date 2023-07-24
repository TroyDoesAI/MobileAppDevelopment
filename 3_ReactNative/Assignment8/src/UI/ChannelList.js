// ChannelList.js

import React, {useLayoutEffect, useState, useEffect} from 'react';
import {FlatList, Text, StyleSheet, TouchableOpacity} from 'react-native';
import ChannelListItem from './ChannelListItem';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {ChannelContext} from '../Model/ChannelViewModel';
import {GET_CHANNELS_FOR_WORKSPACE} from '../Repo/ChannelRepo';

const ChannelList = ({navigation, route}) => {
  const {workspaceName} = route.params;
  const {channels} = React.useContext(ChannelContext); // Consume context

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: () => (
        <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
          {workspaceName}
        </Text>
      ),
      headerLeft: () => (
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.backButton}
          accessibilityLabel="back to workspaces">
          <Icon name="chevron-left" size={25} color="blue" />
          <Text style={styles.backText}>Workspaces</Text>
        </TouchableOpacity>
      ),
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
    });
  }, [navigation, workspaceName]);

  return (
    <FlatList
      data={channels}
      keyExtractor={item => item.id.toString()}
      renderItem={({item}) => (
        <ChannelListItem
          channel={item}
          navigation={navigation}
          workspaceName={workspaceName} // Pass the workspace name to ChannelListItem
        />
      )}
      initialNumToRender={20} // Ensure the first 20 items are rendered initially
    />
  );
};

const styles = StyleSheet.create({
  title: {
    fontSize: 10,
    fontWeight: 'bold',
  },
  backButton: {
    flexDirection: 'row',
    alignItems: 'center',
    marginLeft: 10,
  },
  backText: {
    fontSize: 10,
    marginLeft: 5,
    color: 'blue',
  },
});

export default ChannelList;
