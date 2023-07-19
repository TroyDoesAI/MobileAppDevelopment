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
        guard let url = URL(string: "\(baseUrl)/member") else {
            return
        }

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
        guard let url = URL(string: "\(baseUrl)/login") else {
            return
        }
        
        let lowercaseEmail = email.lowercased()
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
        guard let url = URL(string: "\(baseUrl)/reset") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("Bearer \(user?.accessToken ?? "")", forHTTPHeaderField: "Authorization")
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
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
    
    func loadWorkspaces(withToken token: String) {
        guard let url = URL(string: "\(baseUrl)/workspace") else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                do {
                    let fetchedWorkspaces = try JSONDecoder().decode([Workspace].self, from: data)
                    DispatchQueue.main.async {
                        self.workspaces = fetchedWorkspaces
                    }
                } catch {}
            }
        }
        task.resume()
    }
}

// ObservableObject that provides channel data from API endpoint in JSON format
class ChannelProvider: ObservableObject {
    @Published var channels = [Channel]()
    
    func loadChannels(workspaceId: UUID, withToken token: String) {
        guard let url = URL(string: "\(baseUrl)/workspace/\(workspaceId)/channel") else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                do {
                    let fetchedChannels = try JSONDecoder().decode([Channel].self, from: data)
                    DispatchQueue.main.async {
                        self.channels = fetchedChannels
                    }
                } catch {}
            }
        }
        task.resume()
    }
}

class MessageProvider: ObservableObject {
    @Published var messages = [Message]()
    
    /// Loads messages for the given channel ID using the provided token
    func loadMessages(channelId: UUID, withToken token: String, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseUrl)/channel/\(channelId)/message") else {
            completion(.failure(ServerError.unauthorized)) // replace this error as you needed
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                completion(.failure(error))
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                completion(.failure(ServerError.unauthorized)) // replace this error as you needed
                return
            }

            if let data = data {
                let decoder = JSONDecoder.javaScriptISO8601()
                do {
                    let fetchedMessages = try decoder.decode([Message].self, from: data)
                    DispatchQueue.main.async {
                        self.messages = fetchedMessages
                        completion(.success(()))
                    }
                } catch {
                    completion(.failure(error))
                }
            } else {
                completion(.failure(ServerError.unauthorized)) // replace this error as you needed
            }
        }
        task.resume()
    }

    /// Adds a message to the specified channel
    func addMessage(content: String, channel: Channel, member: Member, withToken token: String, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseUrl)/channel/\(channel.id.uuidString)/message") else {
            completion(.failure(ServerError.unauthorized)) // replace this error as you needed
            return
        }

        let messageContent = ["content": "\(content)"]

        guard let jsonData = try? JSONEncoder().encode(messageContent) else {
            completion(.failure(ServerError.unauthorized)) // replace this error as you needed
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                completion(.failure(error))
                return
            }

            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                completion(.failure(ServerError.unauthorized)) // replace this error as you needed
                return
            }

            self.loadMessages(channelId: channel.id, withToken: token) { result in
                switch result {
                    case .success:
                        completion(.success(()))
                    case .failure(let error):
                        completion(.failure(error))
                }
            }
        }
        task.resume()
    }

    
    func deleteMessage(messageId: UUID, withToken token: String, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseUrl)/message/\(messageId)") else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                completion(.failure(error))
                return
            }

            guard let httpResponse = response as? HTTPURLResponse else {
                return
            }

            if httpResponse.statusCode == 403 {
                completion(.failure(ServerError.unauthorized))
                return
            }

            if (200...299).contains(httpResponse.statusCode) {
                completion(.success(()))
            }
        }
        task.resume()
    }
}
