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

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = view.findViewById(R.id.userName)
        private val posted: TextView = view.findViewById(R.id.date)
        private val content: TextView = view.findViewById(R.id.content)

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

            content.text = message.content.take(80)

            itemView.setOnClickListener {
                onMessageClicked(messageMember)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageMembers[position], onMessageClicked)
    }

    fun getMessageMemberPair(position: Int): Pair<DataClasses.Message, DataClasses.Member> {
        return messageMembers[position]
    }

    override fun getItemCount(): Int {
        return messageMembers.size
    }
}
