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

class MessagesFragment : Fragment() {
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the channel JSON string from the arguments
        val channelJson = arguments?.getString("channel")!!

        // Convert JSON string back to Channel object
        val channel: DataClasses.Channel = Gson().fromJson(channelJson, DataClasses.Channel::class.java)

        (activity as AppCompatActivity).supportActionBar?.title = channel.name

        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        messageAdapter = MessageAdapter(channel.messages.sortedBy { it.date })
        recyclerView.adapter = messageAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.title = "Channels"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
