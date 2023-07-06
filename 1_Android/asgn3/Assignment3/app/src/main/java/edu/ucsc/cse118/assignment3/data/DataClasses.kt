package edu.ucsc.cse118.assignment3.data

import kotlinx.serialization.Serializable

//@Serializable
//data class Workspace(
//    val name: String,
//    val channels: List<Channel>
//)

@Serializable
data class Workspace(
    val name: String,
    val id: String,
    val owner: String = "",
    val channels: Int
)

@Serializable
data class Channel(
    val name: String,
    val messages: List<Message>
)

@Serializable
data class Message(
    val user: User,
    val date: String,
    val content: String
)

@Serializable
data class User(
    val name: String,
    val email: String
)

//@Serializable
//data class Member(
//    val id: String,
//    val name: String,
//    val role: String,
//    val accessToken: String
//)