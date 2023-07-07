package edu.ucsc.cse118.assignment3.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucsc.cse118.assignment3.MessageAdapter
import edu.ucsc.cse118.assignment3.messages.message.MessageFragment
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesFragment : Fragment() {
    private lateinit var messageAdapter: MessageAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroutineScope.launch {
            val channelJson = arguments?.getString("channel") // Get the channel JSON string from the arguments
            if (channelJson != null) {
                val channel = DataClasses.Channel.fromJson(channelJson) // Convert JSON string back to Channel object
                val messages = ApiHandler.getMessages(channel.id) // Get the messages for the channel

                messageAdapter = MessageAdapter(messages, ::onMessageClicked)
                // Find the RecyclerView in the layout and set its layout manager
                val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = messageAdapter

                // Set the ActionBar title here
                (activity as AppCompatActivity).supportActionBar?.title = "${channel.name ?: "Unknown Channel"}" // handle null case
            }
        }
    }

    private fun onMessageClicked(message: DataClasses.Message) {
        // Instantiate the new fragment
        val messageFragment = MessageFragment()

        // Create a new bundle to hold the arguments
        val args = Bundle()

        // Convert message object to JSON
        val messageJson = message.toJson()

        // Add the message JSON string to the arguments
        args.putString("message", messageJson)

        // Set the arguments for the fragment
        messageFragment.arguments = args

        // Replace the current fragment with the new one
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, messageFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
