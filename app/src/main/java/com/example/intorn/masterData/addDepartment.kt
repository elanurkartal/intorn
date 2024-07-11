package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddDepartmentBinding
import com.example.intorn.databinding.FragmentAddUserBinding

class addDepartment : Fragment() {
    lateinit var binding: FragmentAddDepartmentBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddDepartmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        val spinner = binding.spinner
        val items = databaseHelper.getGroups()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.saveButton.setOnClickListener {
            val departmentName=binding.departmentName
            if(departmentName.text.toString().isNotEmpty()){
                databaseHelper.insertDepartment(departmentName.text.toString())
                Toast.makeText(requireContext(), "Department added", Toast.LENGTH_SHORT).show()
                MainActivity.navController.navigate(R.id.department)
            }
            else{
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

}