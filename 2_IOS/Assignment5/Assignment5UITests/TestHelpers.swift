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
# Do not modify this helper class!
#######################################################################
*/

import XCTest

class TestHelpers {
  static func elementCount(app: XCUIApplication, text: String) -> Int {
    let predicate = NSPredicate(format: "label CONTAINS[c] %@", text)
    let elementQuery = app.staticTexts.containing(predicate)
    return elementQuery.count
  }
}
