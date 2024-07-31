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
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddDepartmentBinding
import com.example.intorn.databinding.FragmentAddGroupBinding

class addGroup : Fragment() {
    lateinit var databaseHelper: DatabaseHelper
    lateinit var binding: FragmentAddGroupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        binding.saveButton.setOnClickListener {
            val groupName=binding.editTextGroupName
            if(groupName.text.toString().isNotEmpty() && !databaseHelper.findGroup(groupName.text.toString())){
                databaseHelper.insertGroup(groupName.text.toString())
                Toast.makeText(requireContext(), "Group added", Toast.LENGTH_SHORT).show()
                MainActivity.navController.navigate(R.id.group)
            }
            else{
                Toast.makeText(requireContext(), "Empty or Attribute Already Used", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            MainActivity.navController.navigate(R.id.group)
        }

    }

}