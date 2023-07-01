/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.ucsc.cse118.assignment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      showLoginFragment()
    }
  }

  fun showLoginFragment() {
    supportFragmentManager.commit {
      replace(R.id.fragmentContainerView, LoginFragment::class.java, null)
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }

  fun navigateToWorkspaces() {
    supportFragmentManager.commit {
      replace(R.id.fragmentContainerView, WorkspacesFragment::class.java, null)
      addToBackStack(null)
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    supportFragmentManager.popBackStack()
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    return true
  }
}