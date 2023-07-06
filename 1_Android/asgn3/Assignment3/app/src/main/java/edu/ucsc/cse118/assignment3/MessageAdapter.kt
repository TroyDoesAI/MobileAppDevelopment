package edu.ucsc.cse118.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.data.Message
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private val messages: List<Message>,
    private val onMessageClicked: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    // ViewHolder class to hold and manage the views for each item
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = view.findViewById(R.id.userName)
        private val date: TextView = view.findViewById(R.id.date)
        private val content: TextView = view.findViewById(R.id.content)

        // Binds data to the views and sets the click listener
        fun bind(message: Message, onMessageClicked: (Message) -> Unit) {
            userName.text = message.user.name

            // Format the date string to the desired format
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
            val formattedDate = targetFormat.format(originalFormat.parse(message.date))
            date.text = formattedDate

            // Display only the first 80 characters of the content
            content.text = message.content.take(80)

            // Set the click listener to trigger the specified function when an item is clicked
            itemView.setOnClickListener {
                onMessageClicked(message)
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
        // Bind the message at the current position to the ViewHolder
        holder.bind(messages[position], onMessageClicked)
    }

    // Returns the total number of items in the data set
    override fun getItemCount(): Int {
        return messages.size
    }
}
