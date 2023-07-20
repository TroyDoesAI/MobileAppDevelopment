//
//  Model.swift
//  Assignment5
//
//  Created by Troy Schultz on 7/11/23.
//

import Foundation

// Represents a workspace, which contains multiple channels
struct Workspace: Identifiable, Codable {
    let id: String
    let name: String
    let channels: [Channel]
    
    // Computes the number of unique posters in all channels
    var uniquePosters: Int {
        let members = channels.flatMap { $0.messages.map { $0.member.id } }
        let uniqueMembers = Set(members)
        return uniqueMembers.count
    }

    // Computes the date of the most recent message across all channels
    var mostRecentMessage: Date? {
        channels.compactMap { $0.mostRecentMessage }.max()
    }
}

// Represents a channel, which contains multiple messages
struct Channel: Identifiable, Codable {
    let id: String
    let name: String
    let messages: [Message]
    
    // Computes the number of unique posters in the channel
    var uniquePosters: Int {
        let members = messages.map { $0.member.id }
        let uniqueMembers = Set(members)
        return uniqueMembers.count
    }

    // Computes the date of the most recent message in the channel
    var mostRecentMessage: Date? {
        messages.max { $0.posted < $1.posted }?.posted
    }
}

// Represents a message, which contains the content, posted date, and the member who posted
struct Message: Identifiable, Codable {
    let id: String
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
    @Published var workspaces: [Workspace] = []

    init() {
        loadWorkspaces()
    }

    // Loads workspace data from a JSON file
    func loadWorkspaces() {
        if let url = Bundle.main.url(forResource: "Workspaces", withExtension: "json") {
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                decoder.dateDecodingStrategy = .iso8601
                workspaces = try decoder.decode([Workspace].self, from: data)
            } catch {
                print("Error while parsing json: \(error)")
            }
        }
    }
}
