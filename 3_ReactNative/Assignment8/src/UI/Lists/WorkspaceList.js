/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React, { useState, useEffect } from 'react';
import { FlatList } from 'react-native';
import WorkspaceListItem from '../Cards/WorkspaceListItem';
import workspacesData from '../../Resources/Workspaces.json';

const WorkspaceList = ({ navigation }) => {
    const [workspaces, setWorkspaces] = useState([]);

    useEffect(() => {
        setWorkspaces(workspacesData);
    }, []);

    return (
        <FlatList
            data={workspaces}
            keyExtractor={(item) => item.id} // Use the 'id' property of each item as the key
            renderItem={({ item }) => (
                <WorkspaceListItem
                    workspace={item}
                    navigation={navigation}
                />
            )}
        />
    );
};

export default WorkspaceList;

