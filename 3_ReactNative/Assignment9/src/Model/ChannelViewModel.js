// ChannelViewModel.js

import React from 'react';
import {GET_CHANNELS_FOR_WORKSPACE} from '../Repo/ChannelRepo';

export const ChannelContext = React.createContext();

export const ChannelProvider = props => {
  const [channels, setChannels] = React.useState([]);

  const loadChannelsForWorkspace = async (workspaceId, token) => {
    const fetchedChannels = await GET_CHANNELS_FOR_WORKSPACE(
      workspaceId,
      token,
    );
    setChannels(fetchedChannels); // Update the channels state here, after fetching the data
    return fetchedChannels; // Return the fetched channels data
  };

  return (
    <ChannelContext.Provider value={{channels, loadChannelsForWorkspace}}>
      {props.children}
    </ChannelContext.Provider>
  );
};
