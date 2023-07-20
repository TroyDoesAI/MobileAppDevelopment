///*
// * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
// *
// * You may not use, distribute, publish, or modify this code without
// * the express written permission of the copyright holder.
// */
//
///*
// * The grading system does not simply check the pass/fail of these test; it also
// * checks the contents of the database after each excution to ensure correct
// * functionality.
// */
//
//import XCTest
//
//final class AdvancedUITests: XCTestCase {
//    // Change these to your UCSC email, student number and name
//    static private var email = "traschul@ucsc.edu"
//    static private var passwd = "1815098"
//
//    private var app: XCUIApplication!
//
//    override func setUpWithError() throws {
//      try super.setUpWithError()
//      continueAfterFailure = false
//      app = XCUIApplication()
//      app.launchEnvironment = ["animations": "0"]
//      app.launch()
//    }
//
//    private func waitFor(_ element : XCUIElement, timeout: Double = 5.0) -> XCUIElement {
//      let expectation = expectation(
//        for: NSPredicate(format: "exists == true"),
//        evaluatedWith: element,
//        handler: .none
//      )
//      let _ = XCTWaiter.wait(for: [expectation], timeout: timeout)
//      return element
//    }
//
//    // If you have problems logging in, switch to the slower version below
//    private func login(_ email: String = email, _ passwd: String = passwd) {
//      let field = app.textFields["EMail"]
//      field.tap()
//      field.typeText(email)
//      let password = app.secureTextFields["Password"]
//      password.tap()
//      password.typeText(passwd)
//      app.buttons["Login"].tap()
//    }
//    
//    private func logout() {
//      waitFor(app.navigationBars["Workspaces"].buttons["Logout"]).tap()
//    }
//    
//    func testLoginLogout() throws {
//      login()
//  //    app.navigationBars["Workspaces"].buttons["Logout"].tap()
//      logout()
//      XCTAssert(app.buttons["Login"].exists)
//    }
//    
//  // Each comment block defines a test you need to write, but you can write others
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Assert workspace exists
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Press reset
//   * Assert workspace no longer exists
//   */
//  
//  /*
//   * Log in
//   * Start to add a workspace
//   * Cancel
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Assert workspace exists
//   * Delete the workspace
//   * Assert workspace does not exist
//   */
//  
//  /*
//   * Log in
//   * Select a workspace
//   * Add a channel
//   * Assert channel exists
//   */
//  
//  /*
//   * Log in
//   * Select a workspace
//   * Start to add a channel
//   * Cancel
//   */
//  
//  /*
//   * Log in
//   * Select a workspace
//   * Add a channel
//   * Assert channel exists
//   * Delete the channel
//   * Assert channel does not exist
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Select the workspace
//   * Add Molly Member and Anna Admin as members
//   * Assert Molly and Anna are members of the workspace
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Select the workspaca
//   * Add William Shakespeare as a member
//   * Assert Will is a member of the workspace
//   * Remove Will as a member
//   * Assert Will is no longer a member of the workspace
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Select the workspace
//   * Add William Shakespeare as a member
//   * Log out
//   * Log in as will@cse118.com password "will"
//   * Assert workspace is visiable
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Select the workspace
//   * Add William Shakespeare as a member
//   * Add a channel
//   * Log out
//   * Log in as will@cse118.com password "will"
//   * Select the workspace
//   * Select the channel
//   * Add a new message
//   * Assert message is visible
//   */
//  
//  /*
//   * Log in
//   * Add a workspace
//   * Select the workspace
//   * Add William Shakespeare as a member
//   * Add a channel
//   * Add a new message
//   * Log out
//   * Log in as will@cse118.com password "will"
//   * Select the workspace
//   * Select the channel
//   * Assert message cannot be deleted
//   */
//  
//}
