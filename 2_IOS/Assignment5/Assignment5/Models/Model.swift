//
//  Model.swift
//  Assignment5
//
//  Created by Troy Schultz on 7/11/23.
//

import Foundation

struct Workspace: Identifiable, Codable {
    let id: String
    let name: String
    let channels: [Channel]
}

struct Channel: Identifiable, Codable {
    let id: String
    let name: String
    let messages: [Message]
}

struct Message: Identifiable, Codable {
    let id: String
    let content: String
    let posted: Date
    let member: Member
}

struct Member: Identifiable, Codable {
    let id: String
    let name: String
}

class DataStore: ObservableObject {
    @Published var workspaces: [Workspace] = []

    init() {
        loadWorkspaces()
    }

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
