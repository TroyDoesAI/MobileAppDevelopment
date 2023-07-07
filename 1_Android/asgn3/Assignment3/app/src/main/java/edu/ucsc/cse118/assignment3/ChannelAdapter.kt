package edu.ucsc.cse118.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.data.DataClasses.Channel

class ChannelAdapter(
    private val channels : List<Channel>,
    private val clickListener: (Channel) -> Unit
) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    // ViewHolder class to hold and manage the views for each item
    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.channelNameTextView)
        private val messages: TextView = view.findViewById(R.id.messageCountTextView)

        // Binds data to the views and sets the click listener
        fun bind(channel: Channel, clickListener: (Channel) -> Unit) {
            name.text = channel.name
            var messageString = ""
            messageString = if (channel.messages == 1)
                "${channel.messages} Message"
            else
                "${channel.messages} Messages"
            messages.text = messageString

            // Set the click listener to trigger the specified function when an item is clicked
            itemView.setOnClickListener {
                clickListener(channel)
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        // Inflate the layout for the channel item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_item, parent, false)
        // Create and return a new instance of the ChannelViewHolder
        return ChannelViewHolder(view)
    }

    // Called to display the data at the specified position
    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        // Bind the channel at the current position to the ViewHolder
        holder.bind(channels[position], clickListener)
    }

    // Returns the total number of items in the data set
    override fun getItemCount(): Int {
        return channels.size
    }
}
