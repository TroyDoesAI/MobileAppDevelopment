package edu.ucsc.cse118.assignment3
import android.widget.TextView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import edu.ucsc.cse118.assignment3.messages.MessageAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AdvancedTest {
    fun withHolderMessageText(expectedText: String): Matcher<RecyclerView.ViewHolder> {
        return object: BoundedMatcher<RecyclerView.ViewHolder, MessageAdapter.MessageViewHolder>(
            MessageAdapter.MessageViewHolder::class.java
        ) {
            override fun describeTo(description: Description?) {
                description?.appendText("No ViewHolder found with text: $expectedText")
            }

            override fun matchesSafely(item: MessageAdapter.MessageViewHolder): Boolean {
                val messageTextView: TextView = item.itemView.findViewById(R.id.content)
                return messageTextView.text.toString() == expectedText
            }
        }
    }

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

    @Test
    fun create_new_message_test() {
        // Log in
        typeTo(R.id.email, cruzid)
        typeTo(R.id.password, password)
        clickOn("LOGIN")

        // Navigate to Molly Workspace
        clickOn("Molly Workspace")

        // Navigate to Molly Channel
        clickOn("Molly Channel")

        // Click the FAB (Floating Action Button) icon
        clickOn(R.id.fab)

        // Type the message into the text input
        typeTo(R.id.content, "123456789123456789")

        // Click ADD
        clickOn("ADD")
    }

    @Test
    fun delete_message_test() {
        // Log in
        typeTo(R.id.email, cruzid)
        typeTo(R.id.password, password)
        clickOn("LOGIN")

        // Navigate to Molly Workspace
        clickOn("Molly Workspace")

        // Navigate to Molly Channel
        clickOn("Molly Channel")

        // Swipe the item with the text "123456789123456789" to the right
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnHolderItem(
                withHolderMessageText("123456789123456789"),
                swipeRight()
            )
        )

        // Click "YES" to confirm deletion
        clickOn("YES")
    }
}

