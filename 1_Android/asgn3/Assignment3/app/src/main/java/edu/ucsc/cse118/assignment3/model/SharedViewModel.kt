package edu.ucsc.cse118.assignment3.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SharedViewModel : ViewModel() {
    private val _loginEvent = MutableLiveData<ViewModelEvent<Boolean>>()
    val loginEvent: LiveData<ViewModelEvent<Boolean>> get() = _loginEvent

    companion object{
        var member: Member? = null
        const val url = "https://cse118.com/api/v2/login"
    }
    suspend fun loginUser(email: String, password: String) {
        withContext(Dispatchers.IO) {
            try {
                val memberRepository = MemberRepository()
                member = memberRepository.login(email, password)
                _loginEvent.postValue(ViewModelEvent(true))
            } catch (e: Exception) {
                _loginEvent.postValue(ViewModelEvent(false))
            }
        }
    }

    @Serializable
    data class LoginCredentials(val email: String, val password: String)
}

class MemberRepository {
    fun login(email: String, password: String): Member {
        with(URL(SharedViewModel.url).openConnection() as HttpsURLConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            outputStream.write(Json.encodeToString(
                SharedViewModel.LoginCredentials(
                    email,
                    password
                )
            ).toByteArray())
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return Json.decodeFromString(inputStream.bufferedReader().use { it.readText() })
            } else if (responseCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                throw Exception("Unauthorized: Invalid username or password")
            } else {
                throw Exception("Failed to login: HTTP $responseCode")
            }
        }
    }
}

@Serializable
data class Member(
    val id: String,
    val name: String,
    val role: String,
    val accessToken: String
)
