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
        let interval = calendar.dateComponents([.day], from: date, to: Date())
        if let days = interval.day {
            switch days {
            case 0:
                return "Today"
            case 1:
                return "Yesterday"
            default:
                return "\(days) days"
            }
        } else {
            return "Date calculation error"
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
                    VStack(alignment: .leading) {
                        Text(workspace.name)
                        HStack {
                            Image(systemName: "folder")
                            Text("\(workspace.channels.count) channels")
                        }
                        .accessibilityLabel(Text("Channels: \(workspace.channels.count)"))
                        Text("Unique Posters: \(workspace.uniquePosters)")
                        if let mostRecentMessage = workspace.mostRecentMessage {
                            Text("Latest Message: \(DateFormatterUtil.relativeDateFormat(from: mostRecentMessage))")
                        } else {
                            Text("No recent messages - remove later and leave blank")
                        }
                    }
                }
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
                VStack(alignment: .leading) {
                    Text(channel.name)
                    Text("Messages: \(channel.messages.count)")
                    Text("Unique Posters: \(channel.uniquePosters)")
                    if let mostRecentMessage = channel.mostRecentMessage {
                        Text("Latest Message: \(DateFormatterUtil.relativeDateFormat(from: mostRecentMessage))")
                    } else {
                        Text("No recent messages - remove later and leave blank")
                    }
                    Text("\(channel.messages.count)")
                }
                .accessibilityIdentifier("count for \(channel.name), latest message in \(channel.name)")
            }
        }
        .navigationBarTitle(workspace.name)
    }
}


// Messages sorted by date posted, most recent first
struct MessageListView: View {
    let channel: Channel
    var sortedMessages: [Message] {
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
