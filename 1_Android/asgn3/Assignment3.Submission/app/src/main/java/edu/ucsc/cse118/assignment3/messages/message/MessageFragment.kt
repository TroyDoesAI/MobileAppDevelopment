package edu.ucsc.cse118.assignment3.messages.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import edu.ucsc.cse118.assignment3.ApiHandler
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment : Fragment() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgScope = CoroutineScope(Dispatchers.IO)

    // Called to create the view hierarchy associated with the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for the MessageFragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    // Called immediately after onCreateView() has returned a view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the message JSON string from the arguments
        val messageJson = arguments?.getString("message")
        if (messageJson != null) {
            // Convert JSON string back to Message object
            val message: DataClasses.Message = DataClasses.Message.fromJson(messageJson)

            // Fetch the member name in the background
            bgScope.launch {
                val member = ApiHandler.getMemberById(message.member)

                // Update the UI on the main thread
                uiScope.launch {
                    // Set the action bar title to the user's name
                    (activity as AppCompatActivity).supportActionBar?.title = member.name

                    // Find the date and content TextViews in the layout
                    val dateTextView: TextView = view.findViewById(R.id.date)
                    val contentTextView: TextView = view.findViewById(R.id.content)

                    // Format the date string to the desired format
                    val formats = arrayOf(
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                    )

                    var date: Date? = null

                    for (format in formats) {
                        try {
                            date = format.parse(message.posted)
                            break
                        } catch (e: ParseException) {
                            // Date string doesn't match this format, try the next one
                        }
                    }

                    val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
                    val formattedDate = if (date != null) {
                        targetFormat.format(date)
                    } else {
                        // Handle the case when the date couldn't be parsed
                        "Unknown Date"
                    }

                    // Set the formatted date and content text to the TextViews
                    dateTextView.text = formattedDate
                    contentTextView.text = message.content
                }
            }
        }
    }

    // Called when the view hierarchy associated with the fragment is being removed
    override fun onDestroyView() {
        super.onDestroyView()
        // Show the "Back" button in the action bar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
