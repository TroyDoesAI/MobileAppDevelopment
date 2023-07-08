package edu.ucsc.cse118.assignment3.messages.message

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.ucsc.cse118.assignment3.ApiHandler
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.DataClasses
import edu.ucsc.cse118.assignment3.messages.MessageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateMessageFragment : Fragment() {
    private lateinit var editText: EditText
    private lateinit var messageAdapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        editText = view.findViewById<EditText>(R.id.content)
        editText?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // noop
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // noop
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextListener()
            }
        })
        view.findViewById<Button>(R.id.addButton).setOnClickListener { onAddClicked() }
    }

    private fun onAddClicked() {

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val channelJson = arguments?.getString("channel") // Get the channel JSON string from the arguments
                if (channelJson != null) {
                    var channel = DataClasses.Channel.fromJson(channelJson)
                    var enteredText = editText.text.toString()
                    val message = ApiHandler.addMessage(channel.id, enteredText)
                    // clear the text and show snackbar
                } else {
                     // invalid input, noop
                }

            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


    private fun editTextListener() {
        view?.findViewById<Button>(R.id.addButton)?.isEnabled = editText.text.length >= 16
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}