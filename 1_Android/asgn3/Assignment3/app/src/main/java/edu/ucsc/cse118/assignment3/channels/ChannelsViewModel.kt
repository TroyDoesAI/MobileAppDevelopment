package edu.ucsc.cse118.assignment3.channels

import edu.ucsc.cse118.assignment3.ApiHandler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucsc.cse118.assignment3.data.DataClasses.Channel
import kotlinx.coroutines.launch

class ChannelsViewModel : ViewModel() {
    private val _channelsLiveData = MutableLiveData<List<Channel>>()
    val channelsLiveData: LiveData<List<Channel>> = _channelsLiveData

    fun fetchChannels(workspaceId: String) {
        viewModelScope.launch {
            try {
                val channelList = ApiHandler.getChannels(workspaceId)
                _channelsLiveData.value = channelList
            } catch (e: Exception) {
                // Log the exception
                Log.e("ChannelsViewModel", "Error fetching channels", e)
            }
        }
    }
}
