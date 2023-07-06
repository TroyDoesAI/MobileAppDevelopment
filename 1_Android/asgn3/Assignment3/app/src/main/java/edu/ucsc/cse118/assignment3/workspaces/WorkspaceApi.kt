import edu.ucsc.cse118.assignment3.data.Workspace
import edu.ucsc.cse118.assignment3.model.SharedViewModel.Companion.member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object WorkspaceApi {

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
}
