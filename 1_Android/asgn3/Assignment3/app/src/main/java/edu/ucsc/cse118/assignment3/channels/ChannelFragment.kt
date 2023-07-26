package edu.ucsc.cse118.assignment3.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucsc.cse118.assignment3.messages.MessagesFragment
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses

class ChannelsFragment : Fragment() {
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var channelsViewModel: ChannelsViewModel
    private var workspaceJson: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workspaceJson = arguments?.getString("workspace")
        return inflater.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        channelsViewModel = ViewModelProvider(this).get(ChannelsViewModel::class.java)

        workspaceJson?.let {
            val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(it)
            channelsViewModel.fetchChannels(workspace.id)
        }

        channelsViewModel.channelsLiveData.observe(viewLifecycleOwner) { channels ->
            channelAdapter = ChannelAdapter(channels, ::onChannelClicked)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = channelAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        workspaceJson?.let {
            val workspace: DataClasses.Workspace = DataClasses.Workspace.fromJson(it)
            (activity as AppCompatActivity).supportActionBar?.title = workspace.name
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
