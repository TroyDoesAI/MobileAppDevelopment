package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject

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

        val workspaceJson = arguments?.getString("workspace")

        if (workspaceJson != null) {
            val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(workspaceJson)

            (activity as AppCompatActivity).supportActionBar?.title = workspace.name

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(context)

            channelAdapter = ChannelAdapter(workspace.channels, ::onChannelClicked)
            recyclerView.adapter = channelAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        val workspaceJson = arguments?.getString("workspace")

        if (workspaceJson != null) {
            val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(workspaceJson)
            (activity as AppCompatActivity).supportActionBar?.title = workspace.name
        }
    }

    fun onChannelClicked(channel: DataClasses.Channel) {
        val messagesFragment = MessagesFragment()
        val args = Bundle()

        val channelJson = channel.toJson()

        args.putString("channel", channelJson)
        messagesFragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, messagesFragment)
            .addToBackStack(null)
            .commit()
    }
}
