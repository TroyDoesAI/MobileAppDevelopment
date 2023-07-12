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

import SwiftUI

// Main application entry point
@main
struct Assignment5App: App {
    @StateObject private var dataStore = WorkspaceProvider()

    var body: some Scene {
        WindowGroup {
            Assignment5View()
                .environmentObject(dataStore)
        }
    }
}
