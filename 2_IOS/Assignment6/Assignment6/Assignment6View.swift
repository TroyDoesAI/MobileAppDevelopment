import SwiftUI
import Combine

struct Assignment6View: View {
    @EnvironmentObject var viewModel: LoginViewModel
    @StateObject private var workspaceProvider = WorkspaceProvider()
    @State private var navigateToLogin: Bool = false

    var body: some View {
        NavigationView {
            if viewModel.user != nil {
                WorkspaceListView(workspaceProvider: workspaceProvider, navigateToLogin: $navigateToLogin)
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
                SecureField("Password", text: $viewModel.password)
                Button("Log In", action: viewModel.login)
                    .disabled(!viewModel.isValid)
                
                Spacer() // Pushes the VStack to the top
            }
            .padding()
        }
    }
}

class LoginViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""
    @Published var isValid = false
    @Published var user: User?
    
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
                NavigationLink(destination: ChannelListView(workspace: workspace)) {
                    VStack(alignment: .leading) {
                        Text(workspace.name).font(.headline)
                        Text("Unique Posters: \(workspace.uniquePosters)")
                        if let date = workspace.mostRecentMessage {
                            Text("Most Recent Message: \(date)")
                        }
                    }
                }
            }
        }
    }
}


struct ChannelListView: View {
    let workspace: Workspace
    
    var body: some View {
        List(workspace.channels) { channel in
            NavigationLink(destination: MessageListView(channel: channel)) {
                VStack(alignment: .leading) {
                    Text(channel.name).font(.headline)
                    Text("Unique Posters: \(channel.uniquePosters)")
                    if let date = channel.mostRecentMessage {
                        Text("Most Recent Message: \(date)")
                    }
                }
            }
        }
        .navigationTitle(workspace.name)
    }
}

struct MessageListView: View {
    let channel: Channel
    
    var body: some View {
        List(channel.messages) { message in
            VStack(alignment: .leading) {
                Text(message.content)
                Text("Posted by: \(message.member.name)")
                Text("Posted at: \(message.posted)")
            }
        }
        .navigationTitle(channel.name)
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

