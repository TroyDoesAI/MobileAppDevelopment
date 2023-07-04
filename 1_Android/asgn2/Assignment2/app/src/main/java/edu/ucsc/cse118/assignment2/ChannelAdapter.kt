package edu.ucsc.cse118.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChannelAdapter(
    private val channels: List<DataClasses.Channel>,
    private val clickListener: (DataClasses.Channel) -> Unit
) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    // ViewHolder class to hold and manage the views for each item
    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.channelNameTextView)
        private val messages: TextView = view.findViewById(R.id.messageCountTextView)

        // Binds data to the views and sets the click listener
        fun bind(channel: DataClasses.Channel, clickListener: (DataClasses.Channel) -> Unit) {
            name.text = channel.name
            messages.text = "${channel.messages.size} Messages"

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
