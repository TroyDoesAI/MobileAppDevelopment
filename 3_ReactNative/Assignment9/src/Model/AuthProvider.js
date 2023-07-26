// src/Model/AuthProvider.js
import React, { useState } from 'react';
import AuthContext from './AuthContext';

const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  const signIn = newToken => {
    setToken(newToken);
  };

  const signOut = () => {
    setToken(null);
  };

  return (
    <AuthContext.Provider value={{ token, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
