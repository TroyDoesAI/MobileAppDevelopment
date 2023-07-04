package edu.ucsc.cse118.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private val messages: List<DataClasses.Message>,
    private val onMessageClicked: (DataClasses.Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = view.findViewById(R.id.userName)
        private val date: TextView = view.findViewById(R.id.date)
        private val content: TextView = view.findViewById(R.id.content)

        fun bind(message: DataClasses.Message, onMessageClicked: (DataClasses.Message) -> Unit) {
            userName.text = message.user.name

            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
            val formattedDate = targetFormat.format(originalFormat.parse(message.date))
            date.text = formattedDate
            content.text = message.content.take(80) // taking only first 80 characters

            itemView.setOnClickListener {
                onMessageClicked(message)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position], onMessageClicked)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
