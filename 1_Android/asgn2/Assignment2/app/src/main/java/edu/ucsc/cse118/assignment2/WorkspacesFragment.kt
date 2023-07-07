package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class WorkspacesFragment : Fragment() {
    private lateinit var workspaceAdapter: WorkspaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workspaces, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Workspaces"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        val inputStream = resources.openRawResource(R.raw.workspaces)
        val json = inputStream.bufferedReader().use { it.readText() }

        val jsonArray = JSONArray(json)
        val workspaceList = mutableListOf<DataClasses.Workspace>()
        for (i in 0 until jsonArray.length()) {
            workspaceList.add(DataClasses.Workspace.fromJson(jsonArray.getJSONObject(i).toString()))
        }

        workspaceAdapter = WorkspaceAdapter(workspaceList, ::onWorkspaceClicked)
        recyclerView.adapter = workspaceAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "CSE118 Assignment 2"
    }

    fun onWorkspaceClicked(workspace: DataClasses.Workspace) {
        val channelsFragment = ChannelsFragment()
        val args = Bundle()

        val workspaceJson = workspace.toJson()

        args.putString("workspace", workspaceJson)
        channelsFragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, channelsFragment)
            .addToBackStack(null)
            .commit()
    }
}
