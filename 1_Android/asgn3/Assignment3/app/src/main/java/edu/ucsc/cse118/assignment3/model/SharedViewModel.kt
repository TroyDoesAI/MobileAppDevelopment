package edu.ucsc.cse118.assignment3.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class SharedViewModel : ViewModel() {
    private val _loginEvent = MutableLiveData<ViewModelEvent<Boolean>>()
    val loginEvent: LiveData<ViewModelEvent<Boolean>> get() = _loginEvent

    suspend fun loginUser(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val url = URL("https://cse118.com/api/v2/api-docs/#/default/post_login")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.doOutput = true

            val jsonRequest = JSONObject()
            jsonRequest.put("email", email)
            jsonRequest.put("password", password)

            connection.outputStream.use { os ->
                val input = jsonRequest.toString()
                val writer = OutputStreamWriter(os, "UTF-8")
                writer.write(input)
                writer.flush()
                writer.close()
            }

            val responseCode = connection.responseCode
            connection.disconnect()

            if (responseCode == 200) {
                _loginEvent.postValue(ViewModelEvent(true))
            } else {
                // You might want to handle other response codes here
                _loginEvent.postValue(ViewModelEvent(false))
            }
        }
    }
}
