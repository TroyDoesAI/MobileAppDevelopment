export const GET_WORKSPACES = async () => {
    try {
      const workspacesJson = require('../Resources/Workspaces.json');
      // TODO Convert your json into your JavaScript objects, if needed?
      return workspacesJson;
    } catch (error) {
      console.error("Error while parsing json:", error);
    }
  };
  