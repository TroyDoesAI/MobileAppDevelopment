package edu.ucsc.cse118.assignment3.workspaces

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucsc.cse118.assignment3.ApiHandler
import edu.ucsc.cse118.assignment3.data.DataClasses
import kotlinx.coroutines.launch

class WorkspacesViewModel : ViewModel() {
    private val _workspacesLiveData = MutableLiveData<List<DataClasses.Workspace>>()
    val workspacesLiveData: LiveData<List<DataClasses.Workspace>> = _workspacesLiveData

    fun fetchWorkspaces() {
        viewModelScope.launch {
            try {
                val workspaceList = ApiHandler.getWorkspaces()
                _workspacesLiveData.value = workspaceList
            } catch (e: Exception) {
                // Log the exception
                Log.e("WorkspacesViewModel", "Error fetching workspaces", e)
            }
        }
    }
}
