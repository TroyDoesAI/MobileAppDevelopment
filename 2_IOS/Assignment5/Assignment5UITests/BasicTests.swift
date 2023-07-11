/*
#######################################################################
#
# Copyright (C) 2022-2023 David C. Harrison. All right reserved.
#
# You may not use, distribute, publish, or modify this code without
# the express written permission of the copyright holder.
#
#######################################################################
*/

/*
#######################################################################
# Do not modify this test suite!
#######################################################################
*/

import XCTest

final class BasicTests: XCTestCase {
  
  private var app: XCUIApplication!
  
  override func setUpWithError() throws {
    try super.setUpWithError()
    continueAfterFailure = false
    app = XCUIApplication()
    app.launch()
  }
  
  func testWorkspaces() throws {
    XCTAssert(app.staticTexts["Workspaces"].exists)
    XCTAssert(app.staticTexts["Elevator"].exists)
    XCTAssert(app.staticTexts["EIFS"].exists)
  }
  
  func testChannels() throws {
    app.staticTexts["Elevator"].tap()
    XCTAssert(app.staticTexts["Painter"].exists)
    XCTAssert(app.staticTexts["Refridgeration"].exists)
  }
  
  func testMessages() throws {
    app.staticTexts["Elevator"].tap()
    app.staticTexts["Painter"].tap()
    XCTAssert(app.staticTexts["Gideon Huckle"].exists)
    XCTAssert(app.staticTexts["Gabbi Bilsland"].exists)
    XCTAssert(app.staticTexts["Apr 18, 2023 at 7:06 PM"].exists)
    XCTAssert(app.staticTexts["Apr 14, 2022 at 8:06 AM"].exists)
  }
  
  func testMessage() throws {
    app.staticTexts["Elevator"].tap()
    app.staticTexts["Painter"].tap()
    app.staticTexts["Gideon Huckle"].tap()
    XCTAssertEqual(TestHelpers.elementCount(app: app, text: "Gideon Huckle"), 1)
    XCTAssert(app.staticTexts["Aug 26, 2022 at 9:29 AM"].exists)
  }
  
  func testNavigationChannels() throws {
    app.staticTexts["Elevator"].tap()
    app.navigationBars.buttons["Workspaces"].tap()
  }
  
  func testNavigationMessages() throws {
    app.staticTexts["Elevator"].tap()
    app.staticTexts["Painter"].tap()
    app.navigationBars.buttons["Elevator"].tap()
    app.navigationBars.buttons["Workspaces"].tap()
  }
  
  func testNavigationMessage() throws {
    app.staticTexts["Elevator"].tap()
    app.staticTexts["Painter"].tap()
    app.staticTexts["Gideon Huckle"].tap()
    app.navigationBars.buttons["Painter"].tap()
    app.navigationBars.buttons["Elevator"].tap()
    app.navigationBars.buttons["Workspaces"].tap()
  }
}
