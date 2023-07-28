// MessageListItem.js

// Import necessary libraries and components
import React, {useState, useEffect} from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {formatDate} from '../Model/DataUtil';
import MemberRepository from '../Repo/MembersRepo'; // Adjust the path as needed.

// Define the MessageListItem component
const MessageListItem = ({message, navigation, accessToken}) => {
  const [memberName, setMemberName] = useState('Loading...');

  useEffect(() => {
    const fetchMemberName = async () => {
      console.log('\n\n\naccessToken:', accessToken); // Log the passed in accessToken
      const name = await MemberRepository.fetchMemberById(
        message.member.id,
        accessToken,
      );
      setMemberName(name);
    };
    fetchMemberName();
  }, [message.member.id, accessToken]); // Updated dependency array

  const handlePress = () => {
    message.posted = formatDate(message.posted);
    navigation.navigate('MessageDetail', {message: message});
  };

  return (
    <TouchableOpacity onPress={handlePress}>
      <View style={styles.container}>
        <Text style={styles.posterName}>{memberName}</Text>
        <Text style={styles.item}>{message.content}</Text>
        <Text style={styles.date}>{formatDate(message.posted)}</Text>
      </View>
    </TouchableOpacity>
  );
};

// Define the styles for the component
const styles = StyleSheet.create({
  container: {
    flexDirection: 'column',
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  posterName: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
  },
  date: {
    width: '100%',
    paddingLeft: 10,
    fontSize: 14,
    textAlign: 'right',
  },
});

// Export the component
export default MessageListItem;
