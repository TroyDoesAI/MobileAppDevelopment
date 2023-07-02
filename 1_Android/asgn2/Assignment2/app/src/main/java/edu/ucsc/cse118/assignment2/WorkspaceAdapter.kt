package edu.ucsc.cse118.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkspaceAdapter(private val workspaces: List<DataClasses.Workspace>) :
    RecyclerView.Adapter<WorkspaceAdapter.WorkspaceViewHolder>() {

    class WorkspaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.textView)
        private val channelsTextView: TextView = view.findViewById(R.id.channelsTextView)

        fun bind(workspace: DataClasses.Workspace) {
            name.text = workspace.name
            channelsTextView.text = "${workspace.channels.size} channels"
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
