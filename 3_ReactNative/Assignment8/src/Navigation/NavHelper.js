// navigationHelpers.js

export const navigateToMessages = (navigation, channel) => {
  navigation.push('Messages', { 
      messages: channel.messages, 
      workspaceName: channel.workspace 
  });
};

export const navigateToMessageDetail = (navigation, message) => {
  navigation.navigate('MessageDetail', { message });
};
