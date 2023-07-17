//
//  Model.swift
//  Assignment6
//
//  Created by Troy Schultz on 7/15/23.
//

import Foundation

let baseUrl = "https://cse118.com/api/v2"

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

// ObservableObject that provides member data from API endpoint
class MemberProvider: ObservableObject {
    @Published var members = [UUID: Member]()
    
    /// Loads all members using the provider token
    func loadAllMembers(withToken token: String) {
        guard let url = URL(string: "https://cse118.com/api/v2/member") else {
            print("Invalid URL")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error fetching members: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                print("Invalid response")
                return
            }

            if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let fetchedMembers = try decoder.decode([Member].self, from: data)
                    DispatchQueue.main.async {
                        self.members = Dictionary(uniqueKeysWithValues: fetchedMembers.map { ($0.id, $0) })
                    }
                } catch {
                    print("Failed to decode members. Error: \(error)")
                }
            }
        }
        task.resume()
    }
    
    /// Retrieves the name of the member with the given ID
    func memberName(forID memberID: UUID) -> String? {
            return members[memberID]?.name
        }
}

class LoginProvider: ObservableObject {
    @Published var user: User? {
        didSet {
            if let user = user {
                print("User has been logged in with the id: \(user.id)")
            } else {
                print("User has been logged out")
            }
        }
    }
    
    /// Logs in the user with the provided email and password
    func login(email: String, password: String) {
        guard let url = URL(string: "\(baseUrl)/login") else {
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
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
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
    
    /// Logs out the user
    func logout() {
        guard let url = URL(string: "\(baseUrl)/reset") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
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

// ObservableObject that provides workspace data from API endpoint in JSON format
class WorkspaceProvider: ObservableObject {
    @Published var workspaces = [Workspace]()
    
    /// Loads workspaces using the provided token
    func loadWorkspaces(withToken token: String) {
        print("\nIn WorkspaceProvider: loadWorkspaces()")
        guard let url = URL(string: "https://cse118.com/api/v2/workspace") else {
            print("Invalid URL")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        print("\nLoadWorkspaces -> Token \(token)\n")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error making request for workspaces: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                print("Invalid response")
                return
            }

            print("Http Response Status Code: \(httpResponse.statusCode)")
            
            if let data = data {
                let decoder = JSONDecoder.javaScriptISO8601()
                do {
                    let fetchedWorkspaces = try decoder.decode([Workspace].self, from: data)
                    print(fetchedWorkspaces)
                    DispatchQueue.main.async {
                        self.workspaces = fetchedWorkspaces
                    }
                } catch {
                    print("Failed to decode JSON: \(error)")
                }
            }
        }
        task.resume()
    }
}

// ObservableObject that provides channel data from API endpoint in JSON format
class ChannelProvider: ObservableObject {
    @Published var channels = [Channel]()
    
    /// Loads channels for the given workspace ID using the provided token
    func loadChannels(workspaceId: UUID, withToken token: String) {
        print("\n\nIn loadChannels: ")
        guard let url = URL(string: "\(baseUrl)/workspace/\(workspaceId)/channel") else {
            print("Invalid URL")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        print("Request: \(request)")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error making request for channels: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                print("Invalid response")
                return
            }

            if let data = data {
                let decoder = JSONDecoder.javaScriptISO8601()
                do {
                    let fetchedChannels = try decoder.decode([Channel].self, from: data)
                    print("\n\n\nfetched Channels: \(fetchedChannels)")
                    DispatchQueue.main.async {
                        self.channels = fetchedChannels
                    }
                } catch {
                    print("Failed to decode JSON: \(error)")
                }
            }
        }
        task.resume()
    }
}

class MessageProvider: ObservableObject {
    @Published var messages = [Message]()
    
    /// Loads messages for the given channel ID using the provided token
    func loadMessages(channelId: UUID, withToken token: String) {
        guard let url = URL(string: "\(baseUrl)/channel/\(channelId)/message") else {
            print("Invalid URL")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error making request for messages: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                print("Invalid response")
                return
            }

            if let data = data {
                let decoder = JSONDecoder.javaScriptISO8601()
                do {
                    let fetchedMessages = try decoder.decode([Message].self, from: data)
                    DispatchQueue.main.async {
                        self.messages = fetchedMessages
                    }
                } catch {
                    print("Failed to decode JSON: \(error)")
                }
            }
        }
        task.resume()
    }

    /// Adds a message to the specified channel
    func addMessage(content: String, channel: Channel, member: Member, withToken token: String) {
        guard let url = URL(string: "\(baseUrl)/channel/\(channel.id.uuidString)/message") else {
            print("Invalid URL")
            return
        }

        let messageContent = ["content": "\(content)"]

        guard let jsonData = try? JSONEncoder().encode(messageContent) else {
            print("Failed to encode message content")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error adding message: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode)
            else {
                print("Failed to add message. Invalid response")
                return
            }

            print("\n\nHTTP Response: \(String(describing: response))")

            self.loadMessages(channelId: channel.id, withToken: token) // Reload messages after a successful addition
        }

        task.resume()
    }

    /// Deletes the message with the specified ID
    func deleteMessage(messageId: UUID, withToken token: String) {
        guard let url = URL(string: "\(baseUrl)/message/\(messageId)") else {
            print("Invalid URL")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error deleting message: \(error)")
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                print("Failed to delete message. Invalid response")
                return
            }
            print("\n\nHTTP Response: \(String(describing: response))")
        }
        task.resume()
    }
}
