//
//  Model.swift
//  Assignment6
//
//  Created by Troy Schultz on 7/15/23.
//

import Foundation

let baseUrl = "https://cse118.com/api/v2"

enum ServerError: Error {
    case unauthorized
}
/// Represents a workspace, which contains multiple channels
struct Workspace: Identifiable, Codable {
    let id: String
    let name: String
    let owner: String
    let channels: Int
}

/// Represents a channel, which contains multiple messages
struct Channel: Codable, Identifiable {
    let id: UUID
    let name: String
    let messageCount: Int

    enum CodingKeys: String, CodingKey {
        case id, name
        case messageCount = "messages"
    }
}

/// Represents a message, which contains the content, posted date, and the member who posted
struct Message: Codable, Identifiable {
    let id: UUID
    let content: String
    let posted: Date
    let member: UUID
}

/// Represents a member, who posts messages in channels
struct Member: Codable, Identifiable {
    let id: UUID
    let name: String
}

/// ObservableObject that provides member data from API endpoint
class MemberProvider: ObservableObject {
    @Published private(set) var members = [UUID: Member]()
    private let membersQueue = DispatchQueue(label: "com.assignment6.membersQueue")

    func loadAllMembers(withToken token: String) {
        // Force-unwrapping the URL
        let url = URL(string: "\(baseUrl)/member")!

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                do {
                    let fetchedMembers = try JSONDecoder().decode([Member].self, from: data)
                    self.membersQueue.async {
                        let membersDict = Dictionary(uniqueKeysWithValues: fetchedMembers.map { ($0.id, $0) })
                        DispatchQueue.main.async {
                            self.members = membersDict
                        }
                    }
                } catch {}
            }
        }
        task.resume()
    }
    
    func memberName(forID memberID: UUID) -> String? {
        var memberName: String? = nil
        membersQueue.sync {
            memberName = members[memberID]?.name
        }
        return memberName
    }
}

class LoginProvider: ObservableObject {
    @Published var user: User?

    func login(email: String, password: String) {
        let url = URL(string: "\(baseUrl)/login")!

        let lowercaseEmail = email.lowercased()
        let parameters = ["email": lowercaseEmail, "password": password]
        let jsonData = try! JSONEncoder().encode(parameters)

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")

        let task = URLSession.shared.dataTask(with: request) { [weak self] (data, response, error) in
            if let data = data {
                self?.handleLoginResponse(data: data)
            }
        }
        task.resume()
    }
    
    // Moved the decoding logic to a static function as a workaround
    func handleLoginResponse(data: Data) {
        if let user = Self.decodeUser(from: data) {
            DispatchQueue.main.async {
                self.user = user
            }
        }
    }

    static func decodeUser(from data: Data) -> User? {
        return try? JSONDecoder().decode(User.self, from: data)
    }

    func logout() {
        resetUser() // Synchronous operation

        let url = URL(string: "\(baseUrl)/reset")!
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("Bearer \(user?.accessToken ?? "")", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            // Handle network response, if needed
        }
        task.resume()
    }

    private func resetUser() {
        DispatchQueue.main.async {
            self.user = nil
        }
    }

}

// ObservableObject that provides workspace data from API endpoint in JSON format
class WorkspaceProvider: ObservableObject {
    @Published var workspaces = [Workspace]()
    
    func loadWorkspaces(withToken token: String) {
        let url = URL(string: "\(baseUrl)/workspace")!
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { [weak self] (data, response, error) in
            if let data = data {
                self?.handleWorkspaceResponse(data: data)
            }
        }
        task.resume()
    }

    func handleWorkspaceResponse(data: Data) {
        if let fetchedWorkspaces = Self.decodeWorkspaces(from: data) {
            DispatchQueue.main.async {
                self.workspaces = fetchedWorkspaces
            }
        }
    }

    static func decodeWorkspaces(from data: Data) -> [Workspace]? {
        return try? JSONDecoder().decode([Workspace].self, from: data)
    }
}


// ObservableObject that provides channel data from API endpoint in JSON format
class ChannelProvider: ObservableObject {
    @Published var channels = [Channel]()
    
    func loadChannels(workspaceId: UUID, withToken token: String) {
        let url = URL(string: "\(baseUrl)/workspace/\(workspaceId)/channel")!
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        let task = URLSession.shared.dataTask(with: request) { [weak self] (data, response, error) in
            if let data = data {
                self?.handleChannelResponse(data: data)
            }
        }
        task.resume()
    }

    func handleChannelResponse(data: Data) {
        if let fetchedChannels = Self.decodeChannels(from: data) {
            DispatchQueue.main.async {
                self.channels = fetchedChannels
            }
        }
    }

    static func decodeChannels(from data: Data) -> [Channel]? {
        return try? JSONDecoder().decode([Channel].self, from: data)
    }
}

class MessageProvider: ObservableObject {
    @Published var messages = [Message]()
    
    func loadMessages(channelId: UUID, withToken token: String) {
        let url = URL(string: "\(baseUrl)/channel/\(channelId)/message")!
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { [weak self] (data, _, _) in
            if let data = data {
                self?.handleMessageResponse(data: data)
            }
        }
        task.resume()
    }

    func handleMessageResponse(data: Data) {
        let decoder = JSONDecoder.javaScriptISO8601()
        if let fetchedMessages = Self.decodeMessages(decoder: decoder, from: data) {
            DispatchQueue.main.async {
                self.messages = fetchedMessages
            }
        }
    }

    static func decodeMessages(decoder: JSONDecoder, from data: Data) -> [Message]? {
        return try? decoder.decode([Message].self, from: data)
    }

    /// Adds a message to the specified channel
    func addMessage(content: String, channel: Channel, member: Member, withToken token: String) {
        let url = URL(string: "\(baseUrl)/channel/\(channel.id.uuidString)/message")!
        let messageContent = ["content": "\(content)"]
        let jsonData = try! JSONEncoder().encode(messageContent)
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (_, _, _) in
            self.loadMessages(channelId: channel.id, withToken: token)
        }
        task.resume()
    }

    /// Deletes a message by ID using the provided token
    func deleteMessage(messageId: UUID, withToken token: String) {
        let url = URL(string: "\(baseUrl)/message/\(messageId)")!
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (_, _, _) in
            // No action taken after deleting
        }
        task.resume()
    }
}
