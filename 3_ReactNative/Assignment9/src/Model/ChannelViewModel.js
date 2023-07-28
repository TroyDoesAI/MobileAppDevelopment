import React from 'react';
import {GET_CHANNELS_FOR_WORKSPACE} from '../Repo/ChannelRepo';

export const ChannelContext = React.createContext();

export const ChannelProvider = props => {
  const [channels, setChannels] = React.useState([]);

  const loadChannelsForWorkspace = async (workspaceId, token) => {
    setChannels([]);
    const fetchedChannels = await GET_CHANNELS_FOR_WORKSPACE(
      workspaceId,
      token,
    );
    setChannels(fetchedChannels);
  };

  return (
    <ChannelContext.Provider value={{channels, loadChannelsForWorkspace}}>
      {props.children}
    </ChannelContext.Provider>
  );
};
