// MessageViewModel.js

import React, { createContext, useState } from 'react';

export const MessageContext = createContext();

export const MessageProvider = ({ children }) => {
    const [messages, setMessages] = useState([]);

    return (
        <MessageContext.Provider value={{ messages, setMessages }}>
            {children}
        </MessageContext.Provider>
    );
};
