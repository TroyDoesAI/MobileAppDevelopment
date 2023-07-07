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
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var channel: DataClasses.Channel? = null
        var channelName: String?

        bgScope.launch {
//            var messages: List<DataClasses.Message> = listOf()
            val channelJson = arguments?.getString("channel") // Get the channel JSON string from the arguments
            if (channelJson != null) {
                channel = DataClasses.Channel.fromJson(channelJson) // Convert JSON string back to Channel object
                if (channel != null) {
                    // messages = ApiHandler.getMessages(channel!!.id) // Get the messages for the channel
                    val messages = ApiHandler.getMessages(channel!!.id) // Get the messages for the channel
                    val members = messages.map {
                        // Fetch member data for each message
                        val member = ApiHandler.getMemberById(it.member)
                        it to member
                    }
                    uiScope.launch {
                        // Find the RecyclerView in the layout and set its layout manager
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        messageAdapter = MessageAdapter(members, ::onMessageClicked)
                        recyclerView.adapter = messageAdapter

                        (activity as AppCompatActivity).supportActionBar?.title = "${channel?.name ?: "Unknown Channel"}" // handle null case
                    }
                }
//                channelName = channel!!.name
//                println("\n\n")
//                println(channelName)

            }
//            messageAdapter = MessageAdapter(messages, ::onMessageClicked)
//            // Find the RecyclerView in the layout and set its layout manager
//            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = messageAdapter
        }

//        (activity as AppCompatActivity).supportActionBar?.title = "${channel?.name ?: "Unknown Channel"}" // handle null case
    }

    private fun onMessageClicked(messageMemberPair: Pair<DataClasses.Message, DataClasses.Member>) {
        val messageFragment = MessageFragment()

        val args = Bundle()
        val messageJson = messageMemberPair.first.toJson()
        val memberJson = messageMemberPair.second.toJson() // serialize the member to JSON as well

        args.putString("message", messageJson)
        args.putString("member", memberJson) // pass the member as an argument

        messageFragment.arguments = args

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
