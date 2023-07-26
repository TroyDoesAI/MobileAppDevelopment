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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
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
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    messageAdapter = MessageAdapter(members as MutableList<Pair<DataClasses.Message, DataClasses.Member>>, ::onMessageClicked)
                    recyclerView.adapter = messageAdapter
                    (activity as AppCompatActivity).supportActionBar?.title = "${channel?.name ?: "Unknown Channel"}"

                    setUpSwipeToDelete(recyclerView)
                }
            }
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { onFabClicked() }
    }

    private fun onFabClicked(){
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
        val memberJson = messageMemberPair.second.toJson()

        args.putString("message", messageJson)
        args.putString("member", memberJson)
        messageFragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, messageFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpSwipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SimpleCallback(0, RIGHT) { // Enable swipe to right
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Message From " + messageAdapter.getMessageMemberPair(position).second.name + "?")
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                        val messageMemberPair = messageAdapter.getMessageMemberPair(position)
                        val messageId = messageMemberPair.first.id

                        bgScope.launch {
                            ApiHandler.deleteMessage(messageId)
                            withContext(Dispatchers.IO) {
                            Snackbar.make(recyclerView, "Message Deleted", Snackbar.LENGTH_SHORT).show()
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
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}