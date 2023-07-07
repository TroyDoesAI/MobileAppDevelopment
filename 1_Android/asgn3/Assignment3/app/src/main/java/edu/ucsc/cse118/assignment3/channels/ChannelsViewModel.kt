package edu.ucsc.cse118.assignment3.channels

import ApiHandler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucsc.cse118.assignment3.data.DataClasses.Channel
import kotlinx.coroutines.launch
import edu.ucsc.cse118.assignment3.model.SharedViewModel.Companion.member

class ChannelsViewModel : ViewModel() {
    private val _channelsLiveData = MutableLiveData<List<Channel>>()
    val workspacesLiveData: LiveData<List<Channel>> = _channelsLiveData

    fun fetchWorkspaces() {
        viewModelScope.launch {
            try {
                val channelList = ApiHandler.getChannels(member!!.id)
                _channelsLiveData.value = channelList
            } catch (e: Exception) {
                // Log the exception
                Log.e("WorkspacesViewModel", "Error fetching workspaces", e)
            }
        }
    }
}