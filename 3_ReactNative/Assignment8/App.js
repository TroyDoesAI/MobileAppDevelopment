/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// Required React and React Navigation imports.
import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

// UI components.
import WorkspaceList from './src/UI/WorkspaceList';
import ChannelList from './src/UI/ChannelList';
import MessageList from './src/UI/MessageList';
import MessageDetail from './src/UI/MessageDetail';

// Providers (or Contexts) for each Model.
import {WorkspaceProvider} from './src/Model/WorkspaceViewModel';
import {ChannelProvider} from './src/Model/ChannelViewModel';
import {MessageProvider} from './src/Model/MessageViewModel';

// Initialize the Stack navigator.
const Stack = createStackNavigator();

const App = () => {
  return (
    <MessageProvider>
      <WorkspaceProvider>
        <ChannelProvider>
          <NavigationContainer>
            <Stack.Navigator>
              <Stack.Screen name="Workspaces" component={WorkspaceList} />
              <Stack.Screen name="Channels" component={ChannelList} />
              <Stack.Screen
                name="Messages"
                component={MessageList}
                options={({route}) => ({title: route.params.channelName})} // Dynamically set the title
              />
              <Stack.Screen
                name="MessageDetail"
                component={MessageDetail}
                options={({route}) => ({
                  title: route.params.message.member.name,
                  headerBackTitle: route.params.channelName, // Set back button's title
                })}
              />
            </Stack.Navigator>
          </NavigationContainer>
        </ChannelProvider>
      </WorkspaceProvider>
    </MessageProvider>
  );
};
export default App;

// SUMMARY:

// This is the main app entry file that sets up the navigation structure for
// the entire app using React Navigation's stack navigator. Each screen
// (WorkspaceList, ChannelList, MessageList, MessageDetail) is added to the stack.

// The entire app is wrapped in three context providers: WorkspaceProvider,
// ChannelProvider, and MessageProvider. These providers give access to
// workspace, channel, and message data respectively throughout the app.

// When navigating to the MessageDetail screen, the title of the navigation
// bar is dynamically set to display the name of the clicked message. This
// is achieved using the `options` prop of `Stack.Screen` and accessing the
// route parameters passed to the screen.

// The navigation structure is as follows:
// 1. Workspaces -> Displays a list of workspaces.
// 2. Channels -> Displays channels within a selected workspace.
// 3. Messages -> Displays messages within a selected channel.
// 4. MessageDetail -> Shows details of a clicked message.
