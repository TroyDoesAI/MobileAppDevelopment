// MessageViewModel.js

import React, {createContext, useState, useContext} from 'react';
import {GET_MESSAGES_FOR_CHANNEL} from '../Repo/MessageRepo';
import AuthContext from './AuthContext';

export const MessageContext = createContext();

export const MessageProvider = ({children}) => {
  const [messages, setMessages] = useState([]);
  const {token} = useContext(AuthContext);

  const loadMessagesForChannel = async channelId => {
    setMessages([]);
    const fetchedMessages = await GET_MESSAGES_FOR_CHANNEL(channelId, token);
    setMessages(fetchedMessages);
  };

  return (
    <MessageContext.Provider value={{messages, loadMessagesForChannel}}>
      {children}
    </MessageContext.Provider>
  );
};
