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
}

// Represents a channel, which contains multiple messages
struct Channel: Identifiable, Codable {
    let id: String
    let name: String
    let messages: [Message]
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
