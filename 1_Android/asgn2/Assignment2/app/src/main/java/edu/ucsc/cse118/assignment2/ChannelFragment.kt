package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import androidx.recyclerview.widget.LinearLayoutManager

class ChannelsFragment : Fragment() {
    private lateinit var channelAdapter: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the workspace JSON string from the arguments
        val workspaceJson = arguments?.getString("workspace")!!

        // Convert JSON string back to Workspace object
        val workspace: DataClasses.Workspace = Gson().fromJson(workspaceJson, DataClasses.Workspace::class.java)

        (activity as AppCompatActivity).supportActionBar?.title = workspace.name
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = view.findViewById<RecyclerView>(R.id.channelsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = LinearLayoutManager(context)

        channelAdapter = ChannelAdapter(workspace.channels)
        recyclerView.adapter = channelAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.title = "Workspaces"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
