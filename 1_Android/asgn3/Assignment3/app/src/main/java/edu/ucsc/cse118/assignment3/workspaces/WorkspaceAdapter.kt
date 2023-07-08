package edu.ucsc.cse118.assignment3.workspaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses.Workspace
import kotlin.reflect.KFunction1

class WorkspaceAdapter(
    private val workspaces: List<Workspace>,
    private val listener: KFunction1<Workspace, Unit>
) : RecyclerView.Adapter<WorkspaceAdapter.WorkspaceViewHolder>() {

    inner class WorkspaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.textView)
        private val channelsTextView: TextView = view.findViewById(R.id.channelsTextView)

        // Binds data to the views and sets the click listener
        fun bind(workspace: Workspace) {
            name.text = workspace.name

            var channelString = ""
            channelString = if (workspace.channels == 1)
                "${workspace.channels} Channel"
            else
                "${workspace.channels} Channels"

            channelsTextView.text = channelString

            // Set the click listener to trigger the specified function when an item is clicked
            itemView.setOnClickListener {
                listener.invoke(workspace)
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        // Inflate the layout for the workspace item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workspace_item, parent, false)
        // Create and return a new instance of the WorkspaceViewHolder
        return WorkspaceViewHolder(view)
    }

    // Called to display the data at the specified position
    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        // Bind the workspace at the current position to the ViewHolder
        holder.bind(workspaces[position])
    }

    // Returns the total number of items in the data set
    override fun getItemCount(): Int {
        return workspaces.size
    }
}
