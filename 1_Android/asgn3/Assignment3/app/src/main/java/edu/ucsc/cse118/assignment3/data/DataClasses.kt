package edu.ucsc.cse118.assignment3.data

import org.json.JSONArray
import org.json.JSONObject
import kotlinx.serialization.Serializable

class DataClasses {
    @Serializable
    data class Workspace(
        val name: String,
        val id: String,
        val channels: Int,
        var owner: String = "",
    ) {
        companion object {
            fun fromJson(json: String): Workspace {
                val jsonObject = JSONObject(json)
                return Workspace(jsonObject.getString("name"), jsonObject.getString("id"), jsonObject.getInt("channels"))
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("id", id)
            /*
            var channelString = "channel"
            channelString = if (channels == 1)
                "channel"
            else
                "channels"

            jsonObject.put(channelString, channels)
             */
            jsonObject.put("channels", channels)
            return jsonObject.toString()
        }
    }

    @Serializable
    data class Channel(
        val name: String,
        val id: String,
        val messages: Int
    ) {
        companion object {
            fun fromJson(json: String): Channel {
                val jsonObject = JSONObject(json)
                return Channel(jsonObject.getString("name"), jsonObject.getString("id"), jsonObject.getInt("messages"))
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("id", id)
            jsonObject.put("messages", messages)
            return jsonObject.toString()
        }
    }

    @Serializable
    data class Message(
        val id: String,
        val member: String,
        val posted: String,
        val content: String
    ) {
        companion object {
            fun fromJson(json: String): Message {
                val jsonObject = JSONObject(json)
                return Message(jsonObject.getString("id"), jsonObject.getString("member"), jsonObject.getString("posted"), jsonObject.getString("content"))
            }
        }

        fun toJson(): String {
            val jsonObject = JSONObject()
            jsonObject.put("id", id)
            jsonObject.put("member", member)
            jsonObject.put("posted", posted)
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
