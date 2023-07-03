package edu.ucsc.cse118.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KFunction1

class WorkspaceAdapter(private val workspaces: List<DataClasses.Workspace>, private val listener: KFunction1<DataClasses.Workspace, Unit>) :
    RecyclerView.Adapter<WorkspaceAdapter.WorkspaceViewHolder>() {

    interface WorkspaceClickListener {
        fun onWorkspaceClicked(workspace: DataClasses.Workspace)
    }

    inner class WorkspaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.textView)
        private val channelsTextView: TextView = view.findViewById(R.id.channelsTextView)

        fun bind(workspace: DataClasses.Workspace) {
            name.text = workspace.name
            channelsTextView.text = "${workspace.channels.size} Channels"

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
