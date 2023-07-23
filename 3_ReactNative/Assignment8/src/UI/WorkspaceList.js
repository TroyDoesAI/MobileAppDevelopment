/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React, { useState, useEffect } from 'react';
import { ScrollView } from 'react-native';
import WorkspaceListItem from './WorkspaceListItem';
import workspacesData from '../Resources/Workspaces.json';  // Import the JSON directly

const WorkspaceList = ({ navigation }) => {
    const [workspaces, setWorkspaces] = useState([]);

    useEffect(() => {
        setWorkspaces(workspacesData);
    }, []);

    return (
        <ScrollView>
            {workspaces.map(workspace => (
                <WorkspaceListItem 
                    key={workspace.id} 
                    workspace={workspace} 
                    navigation={navigation} 
                />
            ))}
        </ScrollView>
    );
};

export default WorkspaceList;
