/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/*
 * *****************************************************
 * DO NOT MODIFY THIS FILE
 * *****************************************************
 */
package edu.ucsc.cse118.assignment3

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.TreeIterables
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import org.hamcrest.Matcher

object TestHelper {
  private fun searchFor(matcher: Matcher<View>): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> {
        return isRoot()
      }
      override fun getDescription(): String {
        return "searching for view $matcher in the root view"
      }
      override fun perform(uiController: UiController, view: View) {
        val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)
        childViews.forEach {
          if (matcher.matches(it)) {
            return
          }
        }
        throw NoMatchingViewException.Builder()
          .withRootView(view)
          .withViewMatcher(matcher)
          .build()
      }
    }
  }

  @JvmStatic
  fun waitForView(
    viewMatcher: Matcher<View>,
    waitMillis: Int = 5000,
    waitMillisPerTry: Long = 100
  ): ViewInteraction {
    val maxTries = waitMillis / waitMillisPerTry.toInt()
    var ex: Exception = Exception("Error finding a view matching $viewMatcher")
    for (i in 0..maxTries) {
      try {
        onView(isRoot()).perform(searchFor(viewMatcher))
        return onView(viewMatcher)
      } catch (e: Exception) {
        ex = e
        sleep(waitMillisPerTry)
      }
    }
    throw ex
  }

  @JvmStatic
  fun waitForText(text: String): ViewInteraction {
    return waitForView(withText(text))
  }

  @JvmStatic
  fun scrollRecyclerViewTo(text: String) {
    onView(withId(R.id.recyclerview))
      .perform(
        RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
          hasDescendant(withText(text))
        )
      )
    }
  }