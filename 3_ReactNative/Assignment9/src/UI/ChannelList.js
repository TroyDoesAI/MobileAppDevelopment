// ChannelList.js
import React, {useLayoutEffect, useCallback} from 'react';
import {FlatList, Text, StyleSheet} from 'react-native';
import ChannelListItem from './ChannelListItem';
import {ChannelContext} from '../Model/ChannelViewModel';
import Icon from 'react-native-vector-icons/Ionicons'; // <-- Import here

const ChannelList = ({navigation, route}) => {
  const {workspaceName} = route.params;
  const {channels} = React.useContext(ChannelContext);

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
      headerBackAccessibilityLabel: 'back to workspaces',
      headerRight: () => (
        <Icon
          name="add-circle"
          size={24}
          color="#000"
          accessibilityLabel="add channel"
        />
      ),
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
