package edu.ucsc.cse118.assignment3

import edu.ucsc.cse118.assignment3.data.DataClasses.Channel
import edu.ucsc.cse118.assignment3.data.DataClasses.Workspace
import edu.ucsc.cse118.assignment3.data.DataClasses.Message
import edu.ucsc.cse118.assignment3.model.SharedViewModel.Companion.member
import edu.ucsc.cse118.assignment3.data.DataClasses.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ApiHandler {
    private fun makeHttpRequest(method : String, endpt : String ): String {
        val url = URL(endpt)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        connection.setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
        val response = StringBuffer()
        BufferedReader(InputStreamReader(connection.inputStream)).use {
            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            it.close()
        }
        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw Exception("Error response code: ${connection.responseCode}")
        }
        return response.toString()
    }

    suspend fun getWorkspaces(): List<Workspace> = withContext(Dispatchers.IO) {
        val response = makeHttpRequest("GET", "https://cse118.com/api/v2/workspace")
        Json.decodeFromString<List<Workspace>>(response)
    }

    suspend fun getChannels(workspaceId: String): List<Channel> = withContext(Dispatchers.IO) {
        val response = makeHttpRequest("GET", "https://cse118.com/api/v2/workspace/${workspaceId}/channel")
        Json.decodeFromString<List<Channel>>(response)
    }

    suspend fun getMessages(channelId: String): List<Message> = withContext(Dispatchers.IO) {
        val response = makeHttpRequest("GET", "https://cse118.com/api/v2/channel/${channelId}/message")
        Json.decodeFromString<List<Message>>(response)
    }

    suspend fun getMembers(): List<Member> = withContext(Dispatchers.IO) {
        val response = makeHttpRequest("GET", "https://cse118.com/api/v2/member")
        Json.decodeFromString<List<Member>>(response)
    }

    suspend fun getMemberById(memberId: String): Member = withContext(Dispatchers.IO) {
        val allMembers = getMembers()
        allMembers.find { it.id == memberId }
            ?: throw Exception("Member not found with id: $memberId")
    }
}
