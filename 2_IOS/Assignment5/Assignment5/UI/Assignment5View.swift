/*
#######################################################################
#
# Copyright (C) 2022-2023 David C. Harrison. All right reserved.
#
# You may not use, distribute, publish, or modify this code without
# the express written permission of the copyright holder.
#
#######################################################################
*/

// shorthand closure syntax Ref:https://www.hackingwithswift.com/quick-start/beginners/how-to-use-trailing-closures-and-shorthand-syntax
// working with date components Ref:https://developer.apple.com/documentation/foundation/datecomponents
// Layouts with Stacks Ref:https://developer.apple.com/documentation/swiftui/building-layouts-with-stack-views

import SwiftUI

// Utility for formatting Date objects into strings
struct DateFormatterUtil {
    static func formatDate(_ date: Date) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM d, yyyy 'at' h:mm a"
        return formatter.string(from: date)
    }
    
    static func relativeDateFormat(from date: Date) -> String {
        let calendar = Calendar.current
        let interval = calendar.dateComponents([.day, .hour, .minute, .second], from: date, to: Date())
        if let day = interval.day, day > 0 {
            return "\(day) days"
        } else if let hour = interval.hour, hour > 0 {
            return "\(hour) hours"
        } else if let minute = interval.minute, minute > 0 {
            return "\(minute) mins"
        } else if let second = interval.second, second > 0 {
            return "\(second) secs"
        } else {
            return ""
        }
    }
}

// Modified to display the count of channels, unique posters, and recent message info
struct Assignment5View: View {
    @EnvironmentObject var dataStore: WorkspaceProvider

    var body: some View {
        NavigationView {
            List(dataStore.workspaces) { workspace in
                NavigationLink(destination: ChannelListView(workspace: workspace)) {
                    VStack(alignment: .leading, spacing: 8) {
                        Text(workspace.name).bold()
                        HStack(spacing: 8) {
                            Image(systemName: "folder")
                            TextField("", text: .constant("\(workspace.channels.count)")).disabled(true)
                            
                            Image(systemName: "person.3")
                            TextField("", text: .constant("\(workspace.uniquePosters)")).disabled(true)
                            
                            Image(systemName: "clock")
                            if let mostRecentMessage = workspace.mostRecentMessage {
                                TextField("", text: .constant(DateFormatterUtil.relativeDateFormat(from: mostRecentMessage))).disabled(true)
                            } else {
                                TextField("", text: .constant("")).disabled(true)
                            }
                        }
                        .font(.caption) // Font Size for the Horizontal Stack Section
                    }
                }
                .accessibilityIdentifier("count for \(workspace.name) members active in \(workspace.name) latest message in \(workspace.name)")
            }
            .navigationBarTitle("Workspaces")
        }
    }
}

// Modified to display the count of messages, unique posters, and recent message info
struct ChannelListView: View {
    let workspace: Workspace

    var body: some View {
        List(workspace.channels) { channel in
            NavigationLink(destination: MessageListView(channel: channel)) {
                VStack(alignment: .leading, spacing: 8) {
                    Text(channel.name).bold()
                    
                    HStack(spacing: 8) {
                        Image(systemName: "envelope")
                        TextField("", text: .constant("\(channel.messages.count)")).disabled(true)
                        
                        Image(systemName: "person.3")
                        TextField("", text: .constant("\(channel.uniquePosters)")).disabled(true)
                        
                        Image(systemName: "clock")
                        if let mostRecentMessage = channel.mostRecentMessage {
                            TextField("", text: .constant(DateFormatterUtil.relativeDateFormat(from: mostRecentMessage))).disabled(true)
                        } else {
                            TextField("", text: .constant("")).disabled(true)
                        }
                    }
                    .font(.caption) // Font Size for the Horizontal Stack Section
                }
                .accessibilityIdentifier("count for \(channel.name) members active in \(channel.name) latest message in \(channel.name)")
            }
        }
        .navigationBarTitle(workspace.name)
    }
}

// Messages sorted by date posted, most recent first
struct MessageListView: View {
    let channel: Channel // The channel that this view is showing the messages of
    var sortedMessages: [Message] { // Sort the messages array based on the `posted` property in descending order
        channel.messages.sorted { $0.posted > $1.posted }
    }

    var body: some View {
        List(sortedMessages) { message in
            NavigationLink(destination: MessageDetailView(message: message)) {
                VStack(alignment: .leading) {
                    Text(message.member.name).bold()
                    Text(message.content)
                    Text(DateFormatterUtil.formatDate(message.posted))
                        .frame(maxWidth: .infinity, alignment: .trailing)
                }
            }
        }
        .navigationBarTitle(channel.name)
    }
}


// View displaying the detail of a selected message
struct MessageDetailView: View {
    let message: Message

    var body: some View {
        VStack(alignment: .leading) {
            Text(message.content)
            Text(DateFormatterUtil.formatDate(message.posted))
                .frame(maxWidth: .infinity, alignment: .trailing)
            Spacer()
        }
        .padding()
        .navigationBarTitle(message.member.name)
    }
}

// Preview provider for the main view (used in SwiftUI previews)
#if !TESTING
struct Assignment5View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment5View().environmentObject(WorkspaceProvider())
    }
}
#endif
