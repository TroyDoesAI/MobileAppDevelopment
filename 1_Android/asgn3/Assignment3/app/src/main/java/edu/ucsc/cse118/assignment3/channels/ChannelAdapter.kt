package edu.ucsc.cse118.assignment3.channels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses.Channel

class ChannelAdapter(
    private val channels : List<Channel>,
    private val clickListener: (Channel) -> Unit
) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.channelNameTextView)
        private val messages: TextView = view.findViewById(R.id.messageCountTextView)

        fun bind(channel: Channel, clickListener: (Channel) -> Unit) {
            name.text = channel.name
            var messageString = ""
            messageString = if (channel.messages == 1)
                "${channel.messages} Message"
            else
                "${channel.messages} Messages"
            messages.text = messageString

            itemView.setOnClickListener {
                clickListener(channel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_item, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position], clickListener)
    }

    override fun getItemCount(): Int {
        return channels.size
    }
}
