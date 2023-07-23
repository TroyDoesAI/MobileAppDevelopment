// ChannelRepo.js

export const GET_CHANNELS_FOR_WORKSPACE = async (workspaceId) => {
    try {
        const workspacesJson = require('../Resources/Workspaces.json');

        // Find the workspace with the given ID
        const workspace = workspacesJson.find(ws => ws.id === workspaceId);
        
        // If the workspace is found, return its channels, else return an empty array
        return workspace ? workspace.channels : [];
    } catch (error) {
        console.error("Error while parsing json:", error);
    }
};
