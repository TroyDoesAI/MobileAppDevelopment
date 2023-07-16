//
//  Model.swift
//  Assignment6
//
//  Created by Troy Schultz on 7/15/23.
//

import Foundation

let baseUrl = "https://cse118.com/api/v2"

// Represents a workspace, which contains multiple channels
struct Workspace: Identifiable, Codable {
    let id: String
    let name: String
    let owner: String
    let channels: Int
    
//    // Computes the number of unique posters in all channels
//    var uniquePosters: Int {
//        let members = channels.flatMap { $0.messages.map { $0.member.id } }
//        let uniqueMembers = Set(members)
//        return uniqueMembers.count
//    }
//
//    // Computes the date of the most recent message across all channels
//    var mostRecentMessage: Date? {
//        channels.compactMap { $0.mostRecentMessage }.max()
//    }
}

// Represents a channel, which contains multiple messages
struct Channel: Codable, Identifiable {
    let id: UUID
    let name: String
    let messageCount: Int

    enum CodingKeys: String, CodingKey {
        case id, name
        case messageCount = "messages"
    }
}

// Represents a message, which contains the content, posted date, and the member who posted
struct Message: Codable, Identifiable {
    let id: UUID
    let content: String
    let posted: Date
    let member: Member
}


// Represents a member, who posts messages in channels
struct Member: Identifiable, Codable {
    let id: String
    let name: String
}

// ObservableObject that provides workspace data from a JSON file
class WorkspaceProvider: ObservableObject {
    @Published var workspaces = [Workspace]()
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

class ChannelProvider: ObservableObject {
    @Published var channels = [Channel]()
    
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

    // Note: For these two functions, you would need to change the request methods to POST and DELETE respectively,
    // and modify the request bodies to include the information required by your API
    func addMessage(content: String, member: Member, withToken token: String) {
        // Implement API calling code here.
    }

    func deleteMessage(messageId: UUID, withToken token: String) {
        // Implement API calling code here.
    }
}
