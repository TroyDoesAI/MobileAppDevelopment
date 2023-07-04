package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var buttonLogin: Button

    // Called to create the view hierarchy associated with the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for the LoginFragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    // Called immediately after onCreateView() has returned a view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a reference to the MainActivity
        mainActivity = activity as MainActivity

        // Find the login button in the layout
        buttonLogin = view.findViewById(R.id.buttonLogin)

        // Set a click listener for the login button
        buttonLogin.setOnClickListener {
            // Call the navigateToWorkspaces function of the MainActivity
            mainActivity.navigateToWorkspaces()
        }
    }
}
