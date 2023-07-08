package edu.ucsc.cse118.assignment3.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ucsc.cse118.assignment3.ApiHandler
import edu.ucsc.cse118.assignment3.messages.message.MessageFragment
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import edu.ucsc.cse118.assignment3.messages.message.CreateMessageFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import kotlinx.coroutines.withContext

class MessagesFragment : Fragment() {
    private lateinit var messageAdapter: MessageAdapter
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgScope = CoroutineScope(Dispatchers.IO)
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var channel: DataClasses.Channel?

        bgScope.launch {
            val channelJson = arguments?.getString("channel") // Get the channel JSON string from the arguments
            if (channelJson != null) {
                channel = DataClasses.Channel.fromJson(channelJson) // Convert JSON string back to Channel object

                val messages = ApiHandler.getMessages(channel!!.id) // Get the messages for the channel
                // Fetch member data for each message
                val members = messages.map {
                    val member = ApiHandler.getMemberById(it.member)
                    it to member
                }
                uiScope.launch {
                    // Find the RecyclerView in the layout and set its layout manager
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    messageAdapter = MessageAdapter(members as MutableList<Pair<DataClasses.Message, DataClasses.Member>>, ::onMessageClicked) // TODO FIX THIS SHIT LOL!
                    recyclerView.adapter = messageAdapter
                    (activity as AppCompatActivity).supportActionBar?.title = "${channel?.name ?: "Unknown Channel"}" // handle null case

                    // Setup the swipe to delete functionality after setting up the RecyclerView and adapter
                    setUpSwipeToDelete(recyclerView)
                }
            }
        }

        // FAB icon on click listener
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { onFabClicked() }
    }

    private fun onFabClicked(){
        //val createMessageFragment = CreateMessageFragment()
        val createMsgFragment = CreateMessageFragment()
        val args = Bundle()
        args.putString("channel", arguments?.getString("channel"))
        createMsgFragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, createMsgFragment)
            .addToBackStack(null)
            .commit()
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

    // This function sets up the swipe to delete functionality
    private fun setUpSwipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SimpleCallback(0, RIGHT) { // Enable swipe to right
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Not handling drag and drop in this use case
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Message")
                    .setMessage("Are you sure you want to delete this message?")
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                        val messageMemberPair = messageAdapter.getMessageMemberPair(position)
                        val messageId = messageMemberPair.first.id

                        // Perform deletion on a background thread
                        bgScope.launch {
                            val success = ApiHandler.deleteMessage(messageId)
                            withContext(Dispatchers.Main) {
                                if (success) {
                                    // Delete the message from the adapter on the main thread
                                    messageAdapter.deleteMessage(position)
                                    messageAdapter.notifyDataSetChanged() // Notify adapter of the data set change
                                    Snackbar.make(recyclerView, "Message Deleted", Snackbar.LENGTH_SHORT).show()
                                } else {
                                    // Show snackbar without updating the adapter
                                    Snackbar.make(recyclerView, "Failed to delete message", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }

                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                        // Refresh the adapter to restore the swiped item
                        messageAdapter.notifyItemChanged(position)
                        dialogInterface.dismiss()
                    }
                    .show()
            }
        }

        itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper?.attachToRecyclerView(recyclerView) // Attach to the RecyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}