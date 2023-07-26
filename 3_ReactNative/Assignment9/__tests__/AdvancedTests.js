/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * Must be using Node.js Version 18 or above
 * **************************************************************************/

/* **************************************************************************
 * Use your own username and password for these tests
 * **************************************************************************/

it(`Says... I'm Not A Real Test, Delete Me!`, async () => {});

/*
 * Log in
 * Add a workspace
 * Assert workspace exists
 * Delete the workspace
 * Assert workspace does not exist
 */

/*
 * Log in
 * Start to add a workspace
 * Cancel
 * Assert back at workspace list
 */

/*
 * Log in
 * Start to add a workspace
 * Navigate backwards
 * Assert back at workspace list
 */

/*
 * Log in
 * Add a workspace
 * Select the workspace
 * Add a channel
 * Assert channel exists
 * Delete the channel
 * Assert channel does not exist
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Start to add a channel
 * Cancel
 * Assert back at channel list
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select a workspace
 * Start to add a channel
 * Navigate nackwards
 * Assert back at channel list
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select the workspace
 * Add Molly Member and Anna Admin as members
 * Assert Molly and Anna are members of the workspace
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select the workspaca
 * Add William Shakespeare as a member
 * Assert Will is a member of the workspace
 * Remove Will as a member
 * Assert Will is no longer a member of the workspace
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select the workspace
 * Add William Shakespeare as a member
 * Log out
 * Log in as will@cse118.com password "will"
 * Assert workspace is visiable
 * Log out
 * Log in
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select the workspace
 * Add William Shakespeare as a member
 * Add a channel
 * Log out
 * Log in as will@cse118.com password "will"
 * Select the workspace
 * Select the channel
 * Add a new message
 * Assert message is visible
 * Log out
 * Log in
 * Delete the workspace
 */

/*
 * Log in
 * Add a workspace
 * Select the workspace
 * Add William Shakespeare as a member
 * Add a channel
 * Add a new message
 * Log out
 * Log in as will@cse118.com password "will"
 * Select the workspace
 * Select the channel
 * Assert message cannot be deleted
 * Log out
 * Log in
 * Delete the workspace
 */
