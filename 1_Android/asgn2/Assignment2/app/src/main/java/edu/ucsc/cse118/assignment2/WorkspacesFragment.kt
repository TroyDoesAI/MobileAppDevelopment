package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment2.DataClasses.Workspace
import com.google.gson.Gson

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

        // Set up the RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        // Load the JSON from the raw resource
        val inputStream = resources.openRawResource(R.raw.workspaces)
        val json = inputStream.bufferedReader().use { it.readText() }

        // Parse the JSON into a list of Workspace objects
        val gson = Gson()
        val workspaceList = gson.fromJson(json, Array<Workspace>::class.java).toList()

        // Set the list of Workspace objects on the adapter
        workspaceAdapter = WorkspaceAdapter(workspaceList, ::onWorkspaceClicked)
        recyclerView.adapter = workspaceAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "CSE118 Assignment 2"
    }

    fun onWorkspaceClicked(workspace: DataClasses.Workspace) {
        // Instantiate the new fragment
        val channelsFragment = ChannelsFragment()

        // Create a new bundle to hold the arguments
        val args = Bundle()

        // Convert workspace object to JSON
        val workspaceJson = Gson().toJson(workspace)

        // Add the workspace JSON string to the arguments
        args.putString("workspace", workspaceJson)

        // Set the arguments for the fragment
        channelsFragment.arguments = args

        // Replace the current fragment with the new one
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, channelsFragment)
            .addToBackStack(null)
            .commit()
    }


}
