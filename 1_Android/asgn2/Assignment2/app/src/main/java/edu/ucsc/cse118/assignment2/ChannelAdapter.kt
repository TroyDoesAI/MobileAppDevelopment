package edu.ucsc.cse118.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChannelAdapter(private val channels: List<DataClasses.Channel>, private val clickListener: (DataClasses.Channel) -> Unit) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.channelNameTextView)
        private val messages: TextView = view.findViewById(R.id.messageCountTextView)

        fun bind(channel: DataClasses.Channel, clickListener: (DataClasses.Channel) -> Unit) {
            name.text = channel.name
            messages.text = "${channel.messages.size} Messages"

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
