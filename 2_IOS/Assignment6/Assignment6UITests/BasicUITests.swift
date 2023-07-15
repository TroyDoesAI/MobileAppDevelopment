/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * DO NOT MODIFY THIS FILE EXCEPT WERE INDICATED
 * **************************************************************************/

import XCTest

final class BasicUITests: XCTestCase {
  
  // Change these to your UCSC email, student number and name
  static private var email = "<cruzid>@ucsc.edu"
  static private var passwd = "<password>"

  private var app: XCUIApplication!

  override func setUpWithError() throws {
    try super.setUpWithError()
    continueAfterFailure = false
    app = XCUIApplication()
    app.launchEnvironment = ["animations": "0"]
    app.launch()
  }
  
  private func waitFor(_ element : XCUIElement, timeout: Double = 5.0) -> XCUIElement {
    let expectation = expectation(
      for: NSPredicate(format: "exists == true"),
      evaluatedWith: element,
      handler: .none
    )
    let _ = XCTWaiter.wait(for: [expectation], timeout: timeout)
    return element
  }
                   
  // If you have problems logging in, switch to the slower version below
  private func login(_ email: String = email, _ passwd: String = passwd) {
    let field = app.textFields["EMail"]
    field.tap()
    field.typeText(email)
    let password = app.secureTextFields["Password"]
    password.tap()
    password.typeText(passwd)
    app.buttons["Login"].tap()
  }
  
//  private func login(_ email: String = email, _ passwd: String = passwd) {
//    let field = app.textFields["EMail"]
//    field.tap()
//    for char in email {
//      field.typeText(String(char))
//    }
//    let password = app.secureTextFields["Password"]
//    password.tap()
//    for char in passwd {
//      password.typeText(String(char))
//    }
//    app.buttons["Login"].tap()
//  }
  
  private func mollyChannel() {
    login()
    waitFor(app.collectionViews.buttons["Molly Workspace"]).tap()
    waitFor(app.collectionViews.buttons["Molly Channel"]).tap()
  }
  
  private func annaChannel() {
    login()
    waitFor(app.collectionViews.buttons["Anna Workspace"]).tap()
    waitFor(app.collectionViews.buttons["Anna Channel"]).tap()
  }
  
  func testLoginSuccess() throws {
    login()
    XCTAssert(waitFor(app.navigationBars["Workspaces"]).exists)
  }
  
  func testLoginFailure() throws {
    login(BasicUITests.email, "wrong-password")
    XCTAssertFalse(app.navigationBars["Workspaces"].exists)
    XCTAssert(app.buttons["Login"].exists)
  }
  
  func testLoginLogout() throws {
    login()
    app.navigationBars["Workspaces"].buttons["Logout"].tap()
    XCTAssert(app.buttons["Login"].exists)
  }
  
  func testWorkspaces() throws {
    login()
    XCTAssert(waitFor(app.collectionViews.buttons["Anna Workspace"]).exists)
    XCTAssert(app.collectionViews.buttons["Molly Workspace"].exists)
  }
  
  func testWorkspaceCounts() throws {
    login()
    XCTAssert(waitFor(app.staticTexts["1"]).exists)
    XCTAssert(app.staticTexts["2"].exists)
    XCTAssert(app.staticTexts["3"].exists)
  }
  
  func testChannels() throws {
    login()
    waitFor(app.collectionViews.buttons["Molly Workspace"]).tap()
    XCTAssert(app.collectionViews.buttons["Molly Channel"].exists)
  }
  
  func testChannelNavigation() throws {
    login()
    waitFor(app.collectionViews.buttons["Molly Workspace"]).tap()
    app.navigationBars["Molly Workspace"].buttons["Workspaces"].tap()
    XCTAssert(app.collectionViews.buttons["Anna Workspace"].exists)
    XCTAssert(app.collectionViews.buttons["Molly Workspace"].exists)
  }
  
  func testWorkspaceChannelCounts() throws {
    login()
    waitFor(app.collectionViews.buttons["Anna Workspace"]).tap()
    XCTAssert(app.staticTexts["2"].exists)
  }
  
  func testMessageSenders() throws {
    mollyChannel()
    XCTAssert(app.staticTexts["Molly Member"].exists)
    XCTAssert(app.staticTexts["Anna Admin"].exists)
    XCTAssert(app.staticTexts["William Shakespeare"].exists)
  }
  
  func testMessageContents() throws {
    mollyChannel()
    XCTAssert(app.staticTexts["Hello, and welcome to my channel!"].exists)
    XCTAssert(app.staticTexts["Thanks Molly :)"].exists)
    XCTAssert(app.staticTexts["Be not afraid of greatness. Some are born great, some achieve greatness, and others have CSE118 thrust upon them."].exists)
  }
  
  func testMessageDates() throws {
    mollyChannel()
    XCTAssert(app.staticTexts["Apr 23, 2023 at 3:22 AM"].exists)
    XCTAssert(app.staticTexts["May 1, 2023 at 5:18 AM"].exists)
    XCTAssert(app.staticTexts["May 2, 2023 at 7:37 AM"].exists)
  }
  
  func testCancelAddMessage() throws {
    mollyChannel()
    app.navigationBars["Molly Channel"].buttons["New Message"].tap()
    app.buttons["Cancel"].tap()
    XCTAssert(app.navigationBars["Molly Channel"].buttons["New Message"].exists)
  }
  
  func testNavigationAddMessage() throws {
    mollyChannel()
    app.navigationBars["Molly Channel"].buttons["New Message"].tap()
    app.navigationBars["New Message"].buttons["Molly Channel"].tap()
    XCTAssert(app.navigationBars["Molly Channel"].buttons["New Message"].exists)
  }
  
  private func addMessage(_ content: String) {
    mollyChannel()
    app.navigationBars["Molly Channel"].buttons["New Message"].tap()
    let message = app.textViews["Message"]
    message.tap()
    message.typeText(content)
    app.buttons["OK"].tap()
    XCTAssert(app.staticTexts[content].waitForExistence(timeout: 5))
  }
  
  func testAddMessage() throws {
    let content = "New message @ \(Date().description)"
    addMessage(content)
    // Added, so attempt to delete without assertion
    let element = app.staticTexts[content]
    if element.exists {
      element.swipeLeft()
      let button = app.buttons["Delete"]
      if button.exists {
        button.tap()
      }
    }
  }
  
  func testDeleteMessage() throws {
    let content = "Message to delete @ \(Date().description)"
    addMessage(content)
    app.staticTexts[content].swipeLeft()
    app.buttons["Delete"].tap()
    XCTAssertFalse(app.staticTexts[content].exists)
  }
  
  func testUnableToDeleteInUnownedWorkspace() {
    annaChannel()
    waitFor(app.staticTexts["Hello from Anna!"]).swipeLeft()
    XCTAssertFalse(app.buttons["Delete"].exists)
  }
  
  func testAbleToDeleteInUnownedWorkspace() {
    annaChannel()
    waitFor(app.staticTexts["Thanks Anna, lovely to be here."]).swipeLeft()
    XCTAssert(app.buttons["Delete"].exists)
  }
}
