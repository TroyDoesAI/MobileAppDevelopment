package edu.ucsc.cse118.assignment3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import edu.ucsc.cse118.assignment3.login.LoginFragment
import edu.ucsc.cse118.assignment3.workspaces.WorkspacesFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      showLoginFragment()
    }
  }

  private fun showLoginFragment() {
    supportFragmentManager.commit {
      replace(R.id.fragmentContainerView, LoginFragment::class.java, null)
    }
    supportActionBar?.title = "CSE118 Assignment 3"
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }
  
  fun navigateToWorkspaces() {
    supportFragmentManager.commit {
      replace(R.id.fragmentContainerView, WorkspacesFragment::class.java, null)
      addToBackStack(null)
    }
    supportActionBar?.title = "Workspaces"
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    supportFragmentManager.popBackStack()
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    return true
  }
}
