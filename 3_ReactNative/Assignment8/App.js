/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import WorkspaceList from './src/UI/WorkspaceList';
import { WorkspaceProvider } from './src/Model/WorkspaceViewModel';  // Import the WorkspaceProvider
import ChannelList from './src/UI/ChannelList';
import MessageList from './src/UI/MessageList';
import MessageDetail from './src/UI/MessageDetail';

const Stack = createStackNavigator();

const App = () => {
    return (
        <WorkspaceProvider> 
            <NavigationContainer>
                <Stack.Navigator>
                    <Stack.Screen 
                        name="Workspaces" 
                        component={WorkspaceList} 
                    />
                    <Stack.Screen 
                        name="Channels" 
                        component={ChannelList} 
                    />
                    <Stack.Screen 
                        name="Messages" 
                        component={MessageList} 
                    />
                    <Stack.Screen 
                        name="MessageDetail" 
                        component={MessageDetail} 
                    />
                </Stack.Navigator>
            </NavigationContainer>
        </WorkspaceProvider>
    );
};
export default App;

