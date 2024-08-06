package com.example.intorn.masterData

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentUpdateDepartmentBinding
import com.example.intorn.databinding.FragmentUpdateGroupBinding
import com.example.intorn.masterData.article.UpdateGroupArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateDepartment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateDepartment : Fragment() {

    private lateinit var binding: FragmentUpdateDepartmentBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpdateDepartmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        val bundle : UpdateDepartmentArgs by navArgs()
        val departmentModel = bundle.departmentModel

        val spinner = binding.spinner
        val spinnerItems = databaseHelper.getGroups()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val oldDepartmentName = departmentModel.department_name
        binding.departmentName.setText(oldDepartmentName)
        val items = databaseHelper.getGroups()
        binding.spinner.setSelection(items.indexOf(databaseHelper.getGroupNameFromDepartmentName(departmentModel.department_name)))
        Log.v("asdfasdfadfadasdf",departmentModel.department_name)

        binding.updateButton.setOnClickListener {
            val departmentName=binding.departmentName
            val groupId = databaseHelper.getGroupIdFromGroupName(binding.spinner.selectedItem.toString())
            if(departmentName.text.toString().isNotEmpty() && !databaseHelper.findGroup(departmentName.text.toString())){
                databaseHelper.updateDepartment(departmentName.text.toString(),oldDepartmentName,groupId)
                Toast.makeText(requireContext(), "Department updated", Toast.LENGTH_SHORT).show()
                MainActivity.navController.navigate(R.id.department)
            }
            else{
                Toast.makeText(requireContext(), "Empty or Attribute Already Used", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            MainActivity.navController.navigate(R.id.department)
        }
    }
}