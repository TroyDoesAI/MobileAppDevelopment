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

        fun bind(workspace: Workspace) {
            name.text = workspace.name

            var channelString = ""
            channelString = if (workspace.channels == 1)
                "${workspace.channels} Channel"
            else
                "${workspace.channels} Channels"

            channelsTextView.text = channelString

            itemView.setOnClickListener {
                listener.invoke(workspace)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workspace_item, parent, false)
        return WorkspaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        holder.bind(workspaces[position])
    }

    override fun getItemCount(): Int {
        return workspaces.size
    }
}
