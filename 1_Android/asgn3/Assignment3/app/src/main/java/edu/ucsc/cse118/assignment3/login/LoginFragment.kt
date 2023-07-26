package edu.ucsc.cse118.assignment3.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import edu.ucsc.cse118.assignment3.MainActivity
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.model.SharedViewModel
import kotlinx.coroutines.launch
import android.text.Editable
import android.text.TextWatcher

class LoginFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var buttonLogin: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        emailInput = view.findViewById(R.id.email)
        passwordInput = view.findViewById(R.id.password)
        buttonLogin = view.findViewById(R.id.login)
        buttonLogin.isEnabled = false // Disable the login button by default

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buttonLogin.isEnabled =
                    emailInput.text.length >= 4 && passwordInput.text.length >= 4
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        emailInput.addTextChangedListener(watcher)
        passwordInput.addTextChangedListener(watcher)
        buttonLogin.setOnClickListener {
            var email = emailInput.text.toString()
            var password = passwordInput.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    sharedViewModel.loginUser(email, password)
                }
            } else {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        sharedViewModel.loginEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { isLoggedIn ->
                if (!isLoggedIn) { // Show error message in the password input field
                    passwordInput.text =
                        Editable.Factory.getInstance().newEditable("Failed to login : HTTP 401")
                }
            }
        })

        sharedViewModel.navigateToWorkspacesEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                mainActivity.navigateToWorkspaces()
            }
        })
    }
}
