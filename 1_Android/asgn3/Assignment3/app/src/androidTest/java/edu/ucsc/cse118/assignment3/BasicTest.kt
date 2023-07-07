/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/*
 * **********************************************************************
 * DO NOT MODIFY THIS FILE OTHER THAN FOR YOUR CREDENTIALS @ LINES 44-46
 * **********************************************************************
 */

package edu.ucsc.cse118.assignment3

import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaEnabledAssertions.assertDisabled
import com.adevinta.android.barista.assertion.BaristaEnabledAssertions.assertEnabled
import com.adevinta.android.barista.assertion.BaristaHintAssertions.assertHint
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import edu.ucsc.cse118.assignment3.TestHelper.waitForText
import edu.ucsc.cse118.assignment3.TestHelper.waitForView

import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasicTest {
  /**
   * Create and launch the activity under test before each test,
   * and close it after each test.
   */
  @get:Rule
  var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

  // Change these three lines ONLY
  private val cruzid = "traschul@ucsc.edu"
  private val name = "Troy Schultz"
  private val password = "1815098"

  private fun login() {
    typeTo(R.id.email, cruzid)
    typeTo(R.id.password, password)
    clickOn("LOGIN")
  }
  private fun workspace(workspace: String) {
    login()
    waitForText("$workspace Workspace")
    waitForText("$workspace Workspace")
    clickOn("$workspace Workspace")
  }
  private fun channel(workspace: String, channel: String) {
    workspace(workspace)
    waitForText("$channel Channel")
    clickOn("$channel Channel")
  }

  @Test
  fun login_title() {
    assertDisplayed("CSE118 Assignment 3")
  }
  @Test
  fun login_button() {
    assertDisplayed("LOGIN")
  }
  @Test
  fun login_email_hint() {
    assertHint(R.id.email, "Email")
  }
  @Test
  fun login_password_hint() {
    assertHint(R.id.password, "Password")
  }
  @Test
  fun login_button_disabled() {
    assertDisabled(R.id.login)
  }
  @Test
  fun login_short_email() {
    typeTo(R.id.email, "sh")
    typeTo(R.id.password, password)
    assertDisabled(R.id.login)
  }
  @Test
  fun login_short_password() {
    typeTo(R.id.email, cruzid)
    typeTo(R.id.password, "sh")
    assertDisabled(R.id.login)
  }
  @Test
  fun login_button_enabled() {
    typeTo(R.id.email, cruzid)
    typeTo(R.id.password, password)
    assertEnabled(R.id.login)
  }
  @Test
  fun login_error() {
    typeTo(R.id.email, cruzid)
    typeTo(R.id.password, "wrong")
    clickOn("LOGIN")
    waitForView(withText("Failed to login : HTTP 401")) // TODO - Display this on bad login credentials
  }
  @Test
  fun name_in_header() {
    login()
    waitForText(name)
  }
  @Test
  fun workspaces() {
    login()
    waitForText("Molly Workspace")
    waitForText("Anna Workspace")
    waitForText("Student Workspace")
  }
  @Test
  fun workspace_counts() {
    login()
    waitForText("1 Channel")
    waitForText("2 Channels")
    waitForText("3 Channels")
  }
  @Test
  fun channel_counts() {
    workspace("Anna")
    waitForText("2 Messages")
    waitForText("0 Messages")
  }
  @Test
  fun message_member() {
    channel("Molly", "Molly")
    waitForText("William Shakespeare")
  }
  @Test
  fun message_title() {
    channel("Molly", "Molly")
    waitForText("Hello, and welcome to my channel!")
  }
  @Test
  fun message_date() {
    channel("Molly", "Molly")
    waitForText("May 1, 2023, 12:18:48 PM")
  }
}