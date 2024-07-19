package com.example.intorn.staff

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddUserBinding
import com.example.intorn.databinding.FragmentTaxesBinding

class addUserFragment : Fragment() {
    lateinit var binding: FragmentAddUserBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // binding = FragmentAddUserBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(requireContext())

        /*val spinner = binding.spinner
        val items = listOf("User","Manager")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Toast.makeText(activity, items[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

         */

        val spinner = binding.spinner
        val items = listOf("User", "Manager")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        binding.saveButton.setOnClickListener {
            insertUser(
                binding.editTextName.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextPasswordAgain.text.toString(),
                binding.spinner.selectedItem.toString()
            )
        }
        binding.addUserCancel.setOnClickListener {
            MainActivity.navController.navigate(R.id.userFragment)
        }

    }

    private fun insertUser(name: String, password: String, passwordAgain: String, type:String) {

        if (name.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()) {
            if (password != passwordAgain) {
                Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show()
            } else {
                databaseHelper.insertUser(name, password,type)
                MainActivity.navController.navigate(R.id.userFragment)
                Toast.makeText(activity, "User added", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show()
        }
    }
}