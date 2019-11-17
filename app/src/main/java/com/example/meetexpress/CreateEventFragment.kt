package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

/**
 * A simple [Fragment] subclass.
 */
class CreateEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)
        val spinner = layout.findViewById<Spinner>(R.id.categories_spinner)

        spinner.adapter = ArrayAdapter.createFromResource(activity?.applicationContext, R.array.categories, android.R.layout.simple_spinner_dropdown_item)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//                Toast.makeText(activity, "Selected $pos",Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        return layout
    }

}


