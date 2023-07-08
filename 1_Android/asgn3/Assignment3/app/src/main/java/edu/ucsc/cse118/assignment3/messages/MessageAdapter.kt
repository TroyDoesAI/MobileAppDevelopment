package edu.ucsc.cse118.assignment3.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private var messageMembers: MutableList<Pair<DataClasses.Message, DataClasses.Member>>, // Changed to MutableList
    private val onMessageClicked: (Pair<DataClasses.Message, DataClasses.Member>) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    // ViewHolder class to hold and manage the views for each item
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = view.findViewById(R.id.userName)
        private val posted: TextView = view.findViewById(R.id.date)
        private val content: TextView = view.findViewById(R.id.content)

        // Binds data to the views and sets the click listener
        fun bind(messageMember: Pair<DataClasses.Message, DataClasses.Member>, onMessageClicked: (Pair<DataClasses.Message, DataClasses.Member>) -> Unit) {
            val (message, member) = messageMember
            userName.text = member.name

            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

            val formattedPosted = try {
                val date = format.parse(message.posted)
                val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
                targetFormat.format(date)
            } catch (e: ParseException) {
                val fallbackFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                val date = fallbackFormat.parse(message.posted)
                val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
                targetFormat.format(date)
            }

            posted.text = formattedPosted

            // Display only the first 80 characters of the content
            content.text = message.content.take(80)

            // Set the click listener to trigger the specified function when an item is clicked
            itemView.setOnClickListener {
                onMessageClicked(messageMember)
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // Inflate the layout for the message item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        // Create and return a new instance of the MessageViewHolder
        return MessageViewHolder(view)
    }

    // Called to display the data at the specified position
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // Bind the message and member at the current position to the ViewHolder
        holder.bind(messageMembers[position], onMessageClicked)
    }

    fun getMessageMemberPair(position: Int): Pair<DataClasses.Message, DataClasses.Member> {
        return messageMembers[position]
    }

    // Function to remove a message from the list
    fun deleteMessage(position: Int) {
        messageMembers.removeAt(position)
        notifyItemRemoved(position) // Notifies any registered observers that the item previously located at position has been removed from the data set.
        notifyItemRangeChanged(position, itemCount - position) // Update the indices of the remaining items
    }




    // Returns the total number of items in the data set
    override fun getItemCount(): Int {
        return messageMembers.size
    }
}
