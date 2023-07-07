import edu.ucsc.cse118.assignment3.data.DataClasses.Channel
import edu.ucsc.cse118.assignment3.data.DataClasses.Workspace
import edu.ucsc.cse118.assignment3.model.SharedViewModel.Companion.member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ApiHandler {
    private fun makeHttpRequest(method : String, endpt : String ): Pair<String, Int> {
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
        return Pair(response.toString(), connection.responseCode)
    }

    suspend fun getWorkspaces(): List<Workspace> {
        return withContext(Dispatchers.IO) {
            val responsePair = makeHttpRequest("GET", "https://cse118.com/api/v2/workspace")

            if (responsePair.second == HttpURLConnection.HTTP_OK) {
                val workspaces = Json.decodeFromString<List<Workspace>>(responsePair.first.toString())
                workspaces
            }
            else {
                throw Exception("Error response code: ${responsePair.second}")
            }
        }

    }

    suspend fun getChannels(workspaceId: String): List<Channel> {
        return withContext(Dispatchers.IO) {
            val responsePair = makeHttpRequest("GET", "https://cse118.com/api/v2/workspace/$workspaceId/channel")

            if (responsePair.second == HttpURLConnection.HTTP_OK) {
                val channels = Json.decodeFromString<List<Channel>>(responsePair.first.toString())
                channels
            }
            else {
                throw Exception("Error response code: ${responsePair.second}")
            }
        }
    }
/*
    suspend fun getWorkspaces(): List<Workspace> {
        return withContext(Dispatchers.IO) {
            val url = URL("https://cse118.com/api/v2/workspace")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
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
//            print(response)

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val workspaces = Json.decodeFromString<List<Workspace>>(response.toString())
//                print(workspaces)
                workspaces
            }
            else {
                throw Exception("Error response code: ${connection.responseCode}")
            }
        }
    }
 */
}
