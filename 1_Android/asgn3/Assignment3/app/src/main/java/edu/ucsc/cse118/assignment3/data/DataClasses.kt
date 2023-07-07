package edu.ucsc.cse118.assignment3.data

import org.json.JSONArray
import org.json.JSONObject
import kotlinx.serialization.Serializable

class DataClasses {
    @Serializable
    data class Workspace(
        val name: String,
        val id: String,
        val channels: List<Channel>,
        var owner: String = "",
    ) {
        companion object {
            fun fromJson(json: String): Workspace {
                val jsonObject = JSONObject(json)
                val channelsJsonArray = jsonObject.getJSONArray("channels")
                val channels = mutableListOf<Channel>()
                for (i in 0 until channelsJsonArray.length()) {
                    channels.add(Channel.fromJson(channelsJsonArray.getJSONObject(i).toString()))
                }
                return Workspace(jsonObject.getString("name"), jsonObject.getString("id"), channels)
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("id", id)

            val channelsJsonArray = JSONArray()
            channels.forEach { channel ->
                channelsJsonArray.put(JSONObject(channel.toJson()))
            }

            jsonObject.put("channels", channelsJsonArray)

            return jsonObject.toString()
        }
    }

    @Serializable
    data class Channel(
        val name: String,
        val messages: List<Message>
    ) {
        companion object {
            fun fromJson(json: String): Channel {
                val jsonObject = JSONObject(json)
                val messagesJsonArray = jsonObject.getJSONArray("messages")
                val messages = mutableListOf<Message>()
                for (i in 0 until messagesJsonArray.length()) {
                    messages.add(Message.fromJson(messagesJsonArray.getJSONObject(i).toString()))
                }
                return Channel(jsonObject.getString("name"), messages)
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)

            val messagesJsonArray = JSONArray()
            messages.forEach { message ->
                messagesJsonArray.put(JSONObject(message.toJson()))
            }

            jsonObject.put("messages", messagesJsonArray)

            return jsonObject.toString()
        }
    }

    @Serializable
    data class Message(
        val user: User,
        val date: String,
        val content: String
    ) {
        companion object {
            fun fromJson(json: String): Message {
                val jsonObject = JSONObject(json)
                return Message(
                    User.fromJson(jsonObject.getJSONObject("user").toString()),
                    jsonObject.getString("date"),
                    jsonObject.getString("content")
                )
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("user", JSONObject(user.toJson()))
            jsonObject.put("date", date)
            jsonObject.put("content", content)

            return jsonObject.toString()
        }
    }

    @Serializable
    data class User(
        val name: String,
        val email: String
    ) {
        companion object {
            fun fromJson(json: String): User {
                val jsonObject = JSONObject(json)
                return User(
                    jsonObject.getString("name"),
                    jsonObject.getString("email")
                )
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)

            return jsonObject.toString()
        }
    }

}


//
//@Serializable
//data class Workspace(
//    val name: String,
//    val id: String,
//    val owner: String = "",
//    val channels: Int
//)
//
//@Serializable
//data class Channel(
//    val name: String,
//    val messages: List<Message>
//)
//
//
//@Serializable
//data class Message(
//    val user: User,
//    val date: String,
//    val content: String
//)
//
//@Serializable
//data class User(
//    val name: String,
//    val email: String
//)
