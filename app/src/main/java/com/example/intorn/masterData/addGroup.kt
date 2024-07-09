package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.R

class addGroup : Fragment() {
    lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())


        val save: Button = view.findViewById(R.id.button)
        save.setOnClickListener {
            val groupName: EditText = view.findViewById(R.id.editText_group_name)
            if(groupName.text.toString().isNotEmpty()){
                databaseHelper.insertGroup(groupName.text.toString())
                Toast.makeText(requireContext(), "Group added", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

}