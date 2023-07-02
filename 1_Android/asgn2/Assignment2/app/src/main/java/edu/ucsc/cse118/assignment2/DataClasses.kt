package edu.ucsc.cse118.assignment2

class DataClasses {
    data class Workspace(
        val name: String,
        val channels: List<Channel>
    )

    data class Channel(
        val name: String,
        val messages: List<Message>
    )

    data class Message(
        val user: User,
        val date: String,
        val content: String
    )

    data class User(
        val name: String,
        val email: String
    )

}
