// ChannelList.js

import React, {useLayoutEffect, useCallback} from 'react';
import {FlatList, Text, StyleSheet} from 'react-native';
import ChannelListItem from './ChannelListItem';
import {ChannelContext} from '../Model/ChannelViewModel';

const ChannelList = ({navigation, route}) => {
  const {workspaceName} = route.params;
  const {channels} = React.useContext(ChannelContext); // Consume context

  const HeaderTitle = useCallback(
    () => (
      <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
        {workspaceName}
      </Text>
    ),
    [workspaceName],
  );

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: HeaderTitle,
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
      headerBackAccessibilityLabel: 'back to workspaces', // Setting the accessibility label for the default back button
    });
  }, [navigation, HeaderTitle]);

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
});

export default ChannelList;
