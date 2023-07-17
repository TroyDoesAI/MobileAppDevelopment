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
    let member: UUID  // Update the type to UUID
}




// Represents a member, who posts messages in channels
struct Member: Codable, Identifiable {
    let id: UUID
    let name: String
}

class MemberProvider: ObservableObject {
    @Published var members = [UUID: Member]()

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
    
    func memberName(forID memberID: UUID) -> String? {
            return members[memberID]?.name
        }

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



    func deleteMessage(messageId: UUID, withToken token: String) {
        // Your implementation here
    }
}




//The login() function in the LoginViewModel class sends a POST request to the server to log in the user. If the login is successful and the server responds with a user object, the user property in the LoginViewModel is updated.
//
//The logout() function in the LoginViewModel class sends a PUT request to the server to log out the user. If the logout is successful and the server responds with a success status code, the user property in the LoginViewModel is set to nil.




//My code includes the functionality to make a POST request to the API. Let me explain how it works:
//
//When the user presses the "Submit" button, the addMessage() function is triggered within the ComposeMessageView struct.
//
//This function, in turn, calls the addMessage(content:channel:member:withToken:) method from the MessageProvider class, passing in the necessary parameters.
//
//Within the MessageProvider class, the addMessage(content:channel:member:withToken:) function follows these steps:
//
//It first checks if a valid URL can be formed. If not, it exits the function early.
//
//Next, it attempts to encode the message content into JSON format. If this encoding process fails, it also returns early.
//
//Assuming both the URL formation and JSON encoding are successful, the function prepares a URLRequest with the URL, sets the HTTP method to "POST," adds the JSON-encoded message content to the HTTP body, and sets the appropriate HTTP headers.
//
//Finally, it creates a URLSession data task with the prepared request. This task is responsible for sending the request to the server when it is resumed using task.resume().
//
//If the request is successful, meaning the HTTP response code falls within the range of 200-299, the function logs the HTTP response and reloads the messages for the specified channel by calling the loadMessages(channelId:withToken:) method.
//
//In conclusion, the addMessage function in your ComposeMessageView is effectively making a POST request to the API. It utilizes the addMessage(content:channel:member:withToken:) function within the MessageProvider class. If any issues arise during the request or response process, appropriate error messages will be printed.
