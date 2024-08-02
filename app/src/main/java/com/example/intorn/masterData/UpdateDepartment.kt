package com.example.intorn.masterData

import android.os.Bundle
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

        val oldDepartmentName = departmentModel.department_name
        binding.departmentName.setText(oldDepartmentName)



        binding.updateButton.setOnClickListener {
            val departmentName=binding.departmentName
            if(departmentName.text.toString().isNotEmpty() && !databaseHelper.findGroup(departmentName.text.toString())){
                databaseHelper.updateDepartment(departmentName.text.toString(),oldDepartmentName)
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