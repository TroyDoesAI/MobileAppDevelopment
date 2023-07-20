import SwiftUI
import Combine

// Main view that conditionally renders either the workspace list or the login view
// based on the authentication status of the user.
struct Assignment6View: View {
    @EnvironmentObject var viewModel: LoginViewModel
    @StateObject private var workspaceProvider = WorkspaceProvider()
    @StateObject private var channelProvider = ChannelProvider()
    @StateObject private var messageProvider = MessageProvider()
    @StateObject private var memberProvider = MemberProvider()

    var body: some View {
        NavigationView {
            Group {
                // Display WorkspaceListView if user is authenticated, else show LoginView
                if viewModel.user != nil {
                    WorkspaceListView(workspaceProvider: workspaceProvider, channelProvider: channelProvider, messageProvider: messageProvider, memberProvider: memberProvider)
                        .environmentObject(viewModel)
                        .environmentObject(memberProvider)
                } else {
                    LoginView()
                }
            }
        }
    }
}

// View to handle user login
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

                // Input fields for email and password
                TextField("Email", text: $viewModel.email)
                    .accessibilityLabel("EMail")

                SecureField("Password", text: $viewModel.password)
                    .accessibilityLabel("Password")

                // Login button which triggers the login function
                Button("Log In", action: viewModel.login)
                    .disabled(!viewModel.isValid)
                    .accessibilityLabel("Login")

                Spacer() // Pushes the VStack to the top
            }
            .padding()
            .accessibilityIdentifier("LoginView")
        }
    }
}

// ViewModel for handling login functionality and user's session
class LoginViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""
    @Published var isValid = false
    @Published var user: User?
    @Published var selectedWorkspace: Workspace? = nil

    private var cancellableSet: Set<AnyCancellable> = []
    @ObservedObject var loginProvider = LoginProvider() // Add LoginProvider as an ObservedObject

    init() {
        isFormValidPublisher
            .receive(on: RunLoop.main)
            .assign(to: \.isValid, on: self)
            .store(in: &cancellableSet)
        
        // Observe changes in the user from LoginProvider and update our local user property accordingly
        loginProvider.$user
            .assign(to: \.user, on: self)
            .store(in: &cancellableSet)
    }

    // Publisher to check if the form is valid i.e. email and password aren't empty
    private var isFormValidPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest($email, $password)
            .map { email, password in
                return !email.isEmpty && !password.isEmpty
            }
            .eraseToAnyPublisher()
    }

    // Function to log the user in using the login provider
    func login() {
        loginProvider.login(email: email, password: password)
    }

    // Function to log the user out
    func logout() {
        loginProvider.logout()
        user = nil
    }
}

// View that displays a list of workspaces to the user
struct WorkspaceListView: View {
    @EnvironmentObject var viewModel: LoginViewModel
    @ObservedObject var workspaceProvider: WorkspaceProvider
    @ObservedObject var channelProvider: ChannelProvider
    @ObservedObject var messageProvider: MessageProvider
    @ObservedObject var memberProvider: MemberProvider

    var body: some View {
        VStack {
                List(workspaceProvider.workspaces) { workspace in
                        NavigationLink {
                            ChannelListView(workspace: workspace, channelProvider: channelProvider, messageProvider: messageProvider, memberProvider: memberProvider)
                        } label: {
                            Text("\(workspace.name)")
                            Text("\(workspace.channels)")
                        }
                        .accessibilityLabel("\(workspace.name)")
            }
        }
        .onAppear {
            if let userToken = viewModel.user?.accessToken {
                workspaceProvider.loadWorkspaces(withToken: userToken)
            }
        }
        .navigationBarTitle("Workspaces", displayMode: .inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button(action: {
                    viewModel.logout()
                }) {
                    Image(systemName: "rectangle.portrait.and.arrow.right")
                        .font(.title)
                        .padding()
                        .accessibility(identifier: "Logout")
                }
            }
        }
        .accessibilityIdentifier("WorkspaceListView")
    }
}

struct ChannelListView: View {
    let workspace: Workspace
    @ObservedObject var channelProvider: ChannelProvider
    @ObservedObject var messageProvider: MessageProvider
    @EnvironmentObject var viewModel: LoginViewModel
    @ObservedObject var memberProvider: MemberProvider

    var body: some View {
        List(channelProvider.channels) { channel in
            NavigationLink(destination: MessageListView(channel: channel, messageProvider: messageProvider).environmentObject(viewModel).environmentObject(memberProvider)) {
                HStack {
                    Button(action: {}, label: {
                        Text(channel.name).font(.headline).foregroundColor(Color.primary)
                    })
                    .accessibilityIdentifier("\(channel.name) Channel")

                    Spacer() // This will push the next Text to the right
                    Text("\(channel.messageCount)")
                        .accessibilityIdentifier("Messages \(channel.name) ")
                }
            }
        }
        .navigationTitle(workspace.name)
        .onAppear {
            if let workspaceId = UUID(uuidString: workspace.id) {
                channelProvider.loadChannels(workspaceId: workspaceId, withToken: viewModel.user!.accessToken)
            }
        }
    }
}

struct MessageListView: View {
    let channel: Channel
    @ObservedObject var messageProvider: MessageProvider
    @EnvironmentObject var viewModel: LoginViewModel
    @EnvironmentObject var memberProvider: MemberProvider

    private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM d, yyyy 'at' h:mm a"
        return formatter
    }()
    
    var body: some View {
        List {
            ForEach(messageProvider.messages, id: \.id) { message in
                VStack(alignment: .leading) {
                    Text(memberProvider.memberName(forID: message.member)!)
                    Text(message.content).font(.headline)
                    Text(dateFormatter.string(from: message.posted))
                }
                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                    if message.member == viewModel.user?.id {
                        Button(role: .destructive) {
                            deleteMessage(message)
                        } label: {
                            Label("Delete", systemImage: "trash")
                        }
                    }
                }
            }
        }
        .navigationTitle(channel.name)
        .navigationBarItems(trailing:
                                NavigationLink(
                                    destination: ComposeMessageView(messageProvider: messageProvider, channel: channel)
                                        .environmentObject(viewModel),
                                    label: {
                                        Image(systemName: "plus")
                                    }
                                ).accessibilityIdentifier("New Message")
        )
        .onAppear {
            memberProvider.loadAllMembers(withToken: viewModel.user!.accessToken) // Load members
            messageProvider.loadMessages(channelId: channel.id, withToken: viewModel.user!.accessToken)
        }
    }

    private func deleteMessage(_ message: Message) {
        messageProvider.deleteMessage(messageId: message.id, withToken: viewModel.user!.accessToken)
        DispatchQueue.main.async {
            messageProvider.messages.removeAll(where: { $0.id == message.id })
        }
    }
}

struct ComposeMessageView: View {
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var viewModel: LoginViewModel
    @ObservedObject var messageProvider: MessageProvider
    var channel: Channel
    
    @State private var messageContent: String = ""

    var body: some View {
        VStack {
            TextEditor(text: $messageContent)
                .accessibilityIdentifier("Message")
                .padding()
            HStack {
                Spacer()
                
                HStack {
                    Button(action: cancel) {
                        Text("Cancel")
                    }
                    .accessibilityIdentifier("Cancel")
                    
                    Button(action: addMessage) {
                        Text("OK")
                    }
                    .disabled(messageContent.isEmpty)
                    .accessibilityIdentifier("OK")
                }
                Spacer()
            }
            .padding(.horizontal)
            Spacer()
        }
        .padding()
        .navigationBarTitle("New Message", displayMode: .inline)
    }

    private func cancel() {
        presentationMode.wrappedValue.dismiss()
    }

    private func addMessage() {
        // Ensure the user exists
        if let user = viewModel.user {
            let member = user.toMember()
            messageProvider.addMessage(content: messageContent, channel: channel, member: member, withToken: user.accessToken) // Modified here
            DispatchQueue.main.async {
                messageContent = ""
                presentationMode.wrappedValue.dismiss()
            }
        }
    }
}

struct User: Codable, Identifiable {
    let id: UUID
    let name: String
    let role: String
    let accessToken: String
    var selectedWorkspace: Workspace?
    
    // Function to convert User to Member
    func toMember() -> Member {
        return Member(id: self.id, name: self.name)
    }
}

//#if !TESTING
//struct Assignment6View_Previews: PreviewProvider {
//    static var previews: some View {
//        Assignment6View()
//            .environmentObject(LoginViewModel())
//    }
//}
//#endif


