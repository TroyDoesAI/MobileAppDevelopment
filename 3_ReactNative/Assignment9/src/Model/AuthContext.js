// src/Model/AuthContext.js
import {createContext} from 'react';

const AuthContext = createContext({
  token: null, // Access token
  signIn: () => {}, // Function to sign in and set the token
  signOut: () => {}, // Function to sign out and remove the token
});

export default AuthContext;
