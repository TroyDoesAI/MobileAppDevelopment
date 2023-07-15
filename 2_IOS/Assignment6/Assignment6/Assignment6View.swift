import SwiftUI
import Combine

struct Assignment6View: View {
    @StateObject private var viewModel = LoginViewModel()
    
    var body: some View {
        if let user = viewModel.user {
            WorkspaceListView(user: user)
        } else {
            LoginView(viewModel: viewModel)
        }
    }
}

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel
    
    var body: some View {
        VStack {
            TextField("Email", text: $viewModel.email)
            SecureField("Password", text: $viewModel.password)
            Button("Log In", action: viewModel.login)
                .disabled(!viewModel.isValid)
        }
        .padding()
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
}

struct WorkspaceListView: View {
    let user: User

    var body: some View {
        Text("Welcome, \(user.name)")
    }
}

struct User: Codable, Identifiable {
    let id: UUID
    let name: String
    let role: String
    let accessToken: String
}

#if !TESTING
struct MainView_Previews: PreviewProvider {
  static var previews: some View {
    Assignment6View()
  }
}
#endif

