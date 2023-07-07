package edu.ucsc.cse118.assignment3.channels

import ApiHandler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucsc.cse118.assignment3.ChannelAdapter
import edu.ucsc.cse118.assignment3.messages.MessagesFragment
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelsFragment : Fragment() {
    private lateinit var channelAdapter: ChannelAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var workspaceJson: String? = null  // Declare workspaceJson here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the workspace JSON here
        workspaceJson = arguments?.getString("workspace")

        // Inflate the layout for the ChannelsFragment
        return inflater.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope.launch {
            var channels: List<DataClasses.Channel> = listOf()
            workspaceJson?.let {
                val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(it)
                channels = ApiHandler.getChannels(workspace.id)
            }
            channelAdapter = ChannelAdapter(channels, ::onChannelClicked)
            // Find the RecyclerView in the layout and set its layout manager
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = channelAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        // Get the workspace name
        workspaceJson?.let {
            val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(it)
            (activity as AppCompatActivity).supportActionBar?.title = workspace.name
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun onChannelClicked(channel: DataClasses.Channel) {
        // Instantiate the new fragment
        val messagesFragment = MessagesFragment()

        print("channel: ${channel}")

        // Create a new bundle to hold the arguments
        val args = Bundle()

        // Convert channel object to JSON
        val channelJson = channel.toJson()

        // Add the channel JSON string to the arguments
        args.putString("channel", channelJson)

        // Set the arguments for the fragment
        messagesFragment.arguments = args

        // Replace the current fragment with the new one
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, messagesFragment)
            .addToBackStack(null)
            .commit()
    }
}
