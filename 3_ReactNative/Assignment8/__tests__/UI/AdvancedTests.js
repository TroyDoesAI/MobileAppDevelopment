import React from 'react';
import {render} from '@testing-library/react-native';
import WorkspaceListItem from '../../src/UI/WorkspaceListItem';
import {ChannelContext} from '../../src/Model/ChannelViewModel';

const mockContextValue = {
  loadChannelsForWorkspace: jest.fn(),
  // ... other methods and values from the context, if any
};

const customRender = (ui, options) =>
  render(
    <ChannelContext.Provider value={mockContextValue}>
      {ui}
    </ChannelContext.Provider>,
    options,
  );

describe('WorkspaceListItem', () => {
  it('shows an empty string for latest message when there are no messages', () => {
    const mockNavigation = {
      navigate: jest.fn(),
    };

    const workspaceWithoutMessages = {
      name: 'Test Workspace',
      channels: [],
      uniquePosters: 0,
      mostRecentMessage: null,
    };

    const {queryByText, debug} = customRender(
      <WorkspaceListItem
        workspace={workspaceWithoutMessages}
        navigation={mockNavigation}
      />,
    );

    debug();

    expect(queryByText(/Latest:/i)).not.toBeNull();
  });

  // Add any other advanced tests below
});
