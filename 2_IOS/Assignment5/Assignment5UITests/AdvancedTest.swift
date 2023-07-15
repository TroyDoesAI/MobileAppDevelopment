//
//  AdvancedTest.swift
//  Assignment5UITests
//
//  Created by Troy Schultz on 7/14/23.
//

import XCTest

final class AdvancedTest: XCTestCase {
    
    func testWorkspaceCountsAndRecentMessage() {
        // Launch the app
        let app = XCUIApplication()
        app.launch()

        // Verify channel count for a given workspace
        let channelCount = app.textFields["Drywall & Acoustical (MOB)"]
        XCTAssertEqual(channelCount.value as? String, "7")  // replace "5" with the expected channel count

        
    }


    


    

}

