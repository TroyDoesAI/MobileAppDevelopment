
import SwiftUI

@main
struct Assignment6App: App {
    @StateObject private var viewModel = LoginViewModel()

    var body: some Scene {
        WindowGroup {
            Assignment6View()
                .environmentObject(viewModel)
        }
    }
}
