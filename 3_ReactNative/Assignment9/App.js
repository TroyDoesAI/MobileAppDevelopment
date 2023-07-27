/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// App.js

// Required React and React Navigation imports.
import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

// UI components.
import WorkspaceList from './src/UI/WorkspaceList';
import ChannelList from './src/UI/ChannelList';
import MessageList from './src/UI/MessageList';
import MessageDetail from './src/UI/MessageDetail';
import Login from './src/UI/Login'; // Import the new Login component

// Providers (or Contexts) for each Model.
import {WorkspaceProvider} from './src/Model/WorkspaceViewModel';
import {ChannelProvider} from './src/Model/ChannelViewModel';
import {MessageProvider} from './src/Model/MessageViewModel';

import AuthProvider from './src/Model/AuthProvider'; // Import the new AuthProvider

// Initialize the Stack navigator.
const Stack = createStackNavigator();

const App = () => {
  return (
    <AuthProvider>
      <MessageProvider>
        <WorkspaceProvider>
          <ChannelProvider>
            <NavigationContainer>
              <Stack.Navigator initialRouteName="Login">
                <Stack.Screen
                  name="Login"
                  component={Login}
                  options={{headerShown: false}}
                />
                <Stack.Screen name="Workspaces" component={WorkspaceList} />
                <Stack.Screen name="Channels" component={ChannelList} />
                <Stack.Screen
                  name="Messages"
                  component={MessageList}
                  options={({route}) => ({title: route.params.channelName})}
                />
                <Stack.Screen
                  name="MessageDetail"
                  component={MessageDetail}
                  options={({route}) => ({
                    title: route.params.message.member.name,
                    headerBackTitle: route.params.channelName,
                  })}
                />
              </Stack.Navigator>
            </NavigationContainer>
          </ChannelProvider>
        </WorkspaceProvider>
      </MessageProvider>
    </AuthProvider>
  );
};

export default App;
