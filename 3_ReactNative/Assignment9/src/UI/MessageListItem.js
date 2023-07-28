// MessageListItem.js
import React, {useState, useEffect} from 'react';
import {StyleSheet, Text, View, TouchableOpacity, Animated} from 'react-native';
import {Swipeable} from 'react-native-gesture-handler';
import {formatDate} from '../Model/DataUtil';
import MemberRepository from '../Repo/MembersRepo'; // Adjust the path as needed.

const deleteMessage = async (messageId, accessToken) => {
  await fetch(`https://cse118.com/api/v2/message/${messageId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

const MessageListItem = ({message, navigation, accessToken}) => {
  const [memberName, setMemberName] = useState('Loading...');

  useEffect(() => {
    const fetchMemberName = async () => {
      const name = await MemberRepository.fetchMemberById(
        message.member.id,
        accessToken,
      );
      setMemberName(name);
    };
    fetchMemberName();
  }, [message.member.id, accessToken]);

  const handlePress = () => {
    message.posted = formatDate(message.posted);
    navigation.navigate('MessageDetail', {message: message});
  };

  const renderRightActions = (progress, dragX) => {
    const trans = dragX.interpolate({
      inputRange: [0, 50, 100, 101],
      outputRange: [-20, 0, 0, 1],
    });
    return (
      <TouchableOpacity onPress={() => deleteMessage(message.id, accessToken)}>
        <View style={styles.deleteBox}>
          <Animated.Text
            style={[
              styles.deleteText,
              {
                transform: [{translateX: trans}],
              },
            ]}
            accessibilityLabel="delete message">
            Delete
          </Animated.Text>
        </View>
      </TouchableOpacity>
    );
  };

  return (
    <Swipeable renderRightActions={renderRightActions}>
      <TouchableOpacity onPress={handlePress}>
        <View style={styles.container}>
          <Text style={styles.posterName}>{memberName}</Text>
          <Text style={styles.item}>{message.content}</Text>
          <Text style={styles.date}>{formatDate(message.posted)}</Text>
        </View>
      </TouchableOpacity>
    </Swipeable>
  );
};

const styles = StyleSheet.create({
  deleteBox: {
    backgroundColor: 'red',
    justifyContent: 'center',
    alignItems: 'flex-end',
    width: 100,
    height: '100%',
  },
  deleteText: {
    color: 'white',
    padding: 20,
  },
});

export default MessageListItem;
