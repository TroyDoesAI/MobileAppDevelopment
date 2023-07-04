package edu.ucsc.cse118.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageJson = arguments?.getString("message")
        if (messageJson != null) {
            val message: DataClasses.Message = Gson().fromJson(messageJson, DataClasses.Message::class.java)
            (activity as AppCompatActivity).supportActionBar?.title = message.user.name

            val dateTextView: TextView = view.findViewById(R.id.date)
            val contentTextView: TextView = view.findViewById(R.id.content)

            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            val targetFormat = SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.US)
            val formattedDate = targetFormat.format(originalFormat.parse(message.date))

            dateTextView.text = formattedDate
            contentTextView.text = message.content
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
