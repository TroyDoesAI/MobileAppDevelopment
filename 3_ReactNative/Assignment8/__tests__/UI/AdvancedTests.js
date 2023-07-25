// import React from 'react';
// import {render} from '@testing-library/react-native';
// import WorkspaceListItem from '../../src/UI/WorkspaceListItem';
// import {ChannelContext} from '../../src/Model/ChannelViewModel';

// const mockContextValue = {
//   loadChannelsForWorkspace: jest.fn(),
// };

// const customRender = (ui, options) =>
//   render(
//     <ChannelContext.Provider value={mockContextValue}>
//       {ui}
//     </ChannelContext.Provider>,
//     options,
//   );

import React from 'react';
import { render, cleanup, waitFor } from '@testing-library/react-native';
import WorkspaceListItem from '../../src/UI/WorkspaceListItem';
import { ChannelContext } from '../../src/Model/ChannelViewModel';

const mockContextValue = {
  loadChannelsForWorkspace: jest.fn(),
};

const customRender = (ui, options) =>
  render(
    <ChannelContext.Provider value={mockContextValue}>
      {ui}
    </ChannelContext.Provider>,
    options,
  );

afterEach(() => {
  cleanup();
});

/**
 * This test suite focuses on the `WorkspaceListItem` component.
 */
describe('WorkspaceListItem', () => {
  /**
   * This test checks the behavior when there are no messages in a workspace.
   * 1. Mock a workspace without messages.
   * 2. Render the component with this mock data.
   * 3. Ensure that the text "Latest:" is present, indicating no recent messages.
   */
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
    expect(queryByText(/Latest:/i)).not.toBeNull(); // i flag ensures that the test is not case-specific
  });

  /**
   * Group of tests focusing on how elapsed time since the most recent message is displayed.
   */
  describe('Time Display Tests', () => {
    /**
     * Parametrized test to ensure correct time formatting.
     * - For a message 3 seconds ago, should show "3 secs"
     * - For a message 1 minute ago, should show "1 mins"
     * - For a message 2 hours ago, should show "2 hours"
     * - For a message 2 days ago, should show "2 days"
     *
     * For each scenario:
     * 1. Mock a workspace with the specified most recent message time.
     * 2. Render the component.
     * 3. Check if the expected formatted time text is displayed.
     */
    it.each([
      [new Date(Date.now() - 3000), '3 secs'],
      [new Date(Date.now() - 60000), '1 mins'],
      [new Date(Date.now() - 7200000), '2 hours'],
      [new Date(Date.now() - 86400000 * 2), '2 days'],
      [new Date(0), '19562 days'], // Unix epoch time day 0 is the first of January 1, 1970
    ])('correctly formats elapsed time', async (messageDate, expectedText) => {
      const workspaceWithMessage = {
        name: 'Workspace with Message',
        channels: [],
        uniquePosters: 2, // Mock value
        mostRecentMessage: messageDate,
      };

      const {getByText} = customRender(
        <WorkspaceListItem workspace={workspaceWithMessage} navigation={{}} />,
      );

      await waitFor(() => expect(getByText(`Latest: ${expectedText}`)).toBeDefined());
  });
  });

  describe('Numeric Indicators Test', () => {
    /**
     * Tests if the WorkspaceListItem displays correct counts for channels, messages, etc.
     * 1. Mock a workspace with a set number of channels, messages, etc.
     * 2. Render the component.
     * 3. Check if the counts are displayed correctly.
     */
    it('matches numeric indicators in WorkspaceListItem', () => {
      const workspaceWithCounts = {
        name: 'Workspace with Counts',
        channels: ['Channel1', 'Channel2'],
        uniquePosters: 3,
        mostRecentMessage: new Date(),
      };

      const {getByText} = customRender(
        <WorkspaceListItem workspace={workspaceWithCounts} navigation={{}} />,
      );
      expect(getByText(/Channels: 2/i)).not.toBeNull();
    });
  });

  /**
   * Tests the display of the most recent message from all channels in a workspace.
   */
  describe('Most Recent Message Test', () => {
    /**
     * Tests if the WorkspaceListItem displays the most recent message correctly.
     * 1. Mock a workspace with channels having messages of various dates.
     * 2. Render the component.
     * 3. Check if the most recent message date is displayed correctly.
     */
    it('displays the most recent message across all channels', () => {
      const mockMessages = [
        new Date(Date.now() - 7200000), // 2 hours ago
        new Date(Date.now() - 86400000), // 1 day ago
        new Date(Date.now() - 60000), // 1 minute ago
      ];

      const workspaceWithMessages = {
        name: 'Workspace with Messages',
        channels: mockMessages,
        uniquePosters: 2,
        mostRecentMessage: new Date(Math.max.apply(null, mockMessages)), // Most recent date
      };

      const {getByText} = customRender(
        <WorkspaceListItem workspace={workspaceWithMessages} navigation={{}} />,
      );

      expect(getByText(`Latest: 1 mins`)).not.toBeNull();
    });
  });
});
