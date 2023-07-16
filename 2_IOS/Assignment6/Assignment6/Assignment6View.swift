import SwiftUI
import Combine

struct Assignment6View: View {
    @EnvironmentObject var viewModel: LoginViewModel
    @StateObject private var workspaceProvider = WorkspaceProvider()
    @StateObject private var channelProvider = ChannelProvider()
    @StateObject private var messageProvider = MessageProvider()
    @State private var navigateToLogin: Bool = false

    var body: some View {
        NavigationView {
            if viewModel.user != nil {
                WorkspaceListView(workspaceProvider: workspaceProvider, channelProvider: channelProvider, messageProvider: messageProvider, navigateToLogin: $navigateToLogin)
            } else {
                LoginView()
            }
        }
        .onAppear {
            navigateToLogin = viewModel.user == nil
        }
    }
}

struct LoginView: View {
    @EnvironmentObject var viewModel: LoginViewModel
    
    var body: some View {
        ScrollView {
            VStack {
                Text("CSE118 Assignment 6")
                Image("SlugLogo")
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 150, height: 150)
                    .clipped()
                
                TextField("Email", text: $viewModel.email)
                    .accessibilityLabel("EMail") // Update to use accessibility label instead of identifier
                
                SecureField("Password", text: $viewModel.password)
                    .accessibilityLabel("Password") // Update to use accessibility label instead of identifier
                
                Button("Log In", action: viewModel.login)
                    .disabled(!viewModel.isValid)
                    .accessibilityLabel("Login") // Update to use accessibility label instead of identifier
                
                Spacer() // Pushes the VStack to the top
            }
            .padding()
            .accessibilityIdentifier("LoginView") // Add accessibility identifier to the view
        }
    }
}

class LoginViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""
    @Published var isValid = false
    @Published var user: User? {
        didSet {
            if let user = user {
                print("User has been logged in with the id: \(user.id)")
            } else {
                print("User has been logged out")
            }
        }
    }
    
    private var cancellableSet: Set<AnyCancellable> = []
    
    init() {
        isFormValidPublisher
            .receive(on: RunLoop.main)
            .assign(to: \.isValid, on: self)
            .store(in: &cancellableSet)
    }
    
    private var isFormValidPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest($email, $password)
            .map { email, password in
                return !email.isEmpty && !password.isEmpty
            }
            .eraseToAnyPublisher()
    }
    
    func login() {
        guard let url = URL(string: "https://cse118.com/api/v2/login") else {
            return
        }
        
        let lowercaseEmail = email.lowercased() // Transform email to lowercase
        
        let parameters = ["email": lowercaseEmail, "password": password]
        guard let jsonData = try? JSONEncoder().encode(parameters) else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")  // Adding Accept header

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let _ = error {
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                return
            }
            
            if let data = data,
               let user = try? JSONDecoder().decode(User.self, from: data) {
                DispatchQueue.main.async {
                    self.user = user
                }
            }
        }
        task.resume()
    }
    
    func logout() {
        guard let url = URL(string: "https://cse118.com/api/v2/reset") else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Accept")  // Adding Accept header
        request.addValue("Bearer \(user?.accessToken ?? "")", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let _ = error {
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                return
            }

            DispatchQueue.main.async {
                self.user = nil
            }
        }
        task.resume()
    }
}

struct WorkspaceListView: View {
    @EnvironmentObject var viewModel: LoginViewModel
    @ObservedObject var workspaceProvider: WorkspaceProvider
    @ObservedObject var channelProvider: ChannelProvider
    @ObservedObject var messageProvider: MessageProvider
    @Binding var navigateToLogin: Bool

    var body: some View {
        VStack {
            HStack {
                Button(action: {
                    viewModel.logout()
                    navigateToLogin = true
                }) {
                    Image(systemName: "rectangle.portrait.and.arrow.right")
                        .font(.title)
                        .padding()
                }

                Text("Workspaces")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding()

                Spacer()
            }

            List(workspaceProvider.workspaces) { workspace in
                NavigationLink(destination: ChannelListView(workspace: workspace, channelProvider: channelProvider, messageProvider: messageProvider).environmentObject(viewModel)) {
                    VStack(alignment: .leading) {
                        Text(workspace.name).font(.headline)
                        Text("Owner: \(workspace.owner)")
                        Text("Channels: \(workspace.channels)")
                    }
                }
            }
        }.onAppear {
            workspaceProvider.loadWorkspaces(withToken: viewModel.user?.accessToken ?? "")
        }
        .navigationBarTitle("Workspaces", displayMode: .inline)
    }
}






struct ChannelListView: View {
    let workspace: Workspace
    @ObservedObject var channelProvider: ChannelProvider
    @ObservedObject var messageProvider: MessageProvider
    @EnvironmentObject var viewModel: LoginViewModel
    
    var body: some View {
        List(channelProvider.channels) { channel in
            NavigationLink(destination: MessageListView(channel: channel, messageProvider: messageProvider).environmentObject(viewModel)) {
                VStack(alignment: .leading) {
                    Text(channel.name).font(.headline)
                    Text("Message Count: \(channel.messageCount)")
                }
            }
        }
        .navigationTitle(workspace.name)
        .onAppear {
            if let workspaceId = UUID(uuidString: workspace.id) {
                channelProvider.loadChannels(workspaceId: workspaceId, withToken: viewModel.user?.accessToken ?? "")
            }
        }
    }
}

struct MessageListView: View {
    let channel: Channel
    @ObservedObject var messageProvider: MessageProvider
    @EnvironmentObject var viewModel: LoginViewModel
    
    var body: some View {
        List(messageProvider.messages) { message in
            VStack(alignment: .leading) {
                Text("Posted by: \(message.member)") // This will show member's id, not name
                Text(message.content).font(.headline)
                Text("Posted at: \(message.posted)")
            }
        }
        .navigationTitle(channel.name)
        .onAppear {
            messageProvider.loadMessages(channelId: channel.id, withToken: viewModel.user?.accessToken ?? "")
        }
    }
}

struct User: Codable, Identifiable {
    let id: UUID
    let name: String
    let role: String
    let accessToken: String
}

#if !TESTING
struct Assignment6View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment6View()
            .environmentObject(LoginViewModel())
    }
}
#endif






