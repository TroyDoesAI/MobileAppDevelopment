/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React from 'react';
import {Text} from 'react-native';

const DeleteMe = () => {
  return <Text>Delete Me - I'm only here for the lint!</Text>;
};

export default DeleteMe;


// import React from 'react';
// import { NavigationContainer } from '@react-navigation/native';
// import { createStackNavigator } from '@react-navigation/stack';

// import WorkspaceList from './src/UI/WorkspaceList';
// // TODO import ChannelList from './src/UI/ChannelList';
// // TODO import MessageList from './src/UI/MessageList';
// import WorkspaceViewModel from './src/Model/WorkspaceViewModel'

// const Stack = createStackNavigator();

// const App = () => {
//     return (
//         <WorkspaceViewModel>
//             <NavigationContainer>
//                 <Stack.Navigator>
//                     <Stack.Screen 
//                         name="Workspaces" 
//                         component={WorkspaceList} 
//                     />
//                     {/* <Stack.Screen
//                         name="Channels"
//                         component={ChannelList}
//                         options={({ route }) => ({
//                             title: route.params.title,
//                             headerBackTitle: 'Workspaces'
//                         })}
//                     />
//                     <Stack.Screen
//                         name="Messages"
//                         component={MessageList}
//                         options={({ route }) => ({
//                             title: route.params.title,
//                             headerBackTitle: 'Channels'
//                         })}
//                     /> */}
//                 </Stack.Navigator>
//             </NavigationContainer>
//         </WorkspaceViewModel>
//     );
// };
// export default App;