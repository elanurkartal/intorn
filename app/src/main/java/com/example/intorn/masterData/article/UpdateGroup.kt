package com.example.intorn.masterData.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddGroupBinding
import com.example.intorn.databinding.FragmentUpdateGroupBinding
import com.example.intorn.staff.UserUpdateFragmentArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [UpdateGroup.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateGroup : Fragment() {

    private lateinit var binding: FragmentUpdateGroupBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpdateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        val bundle : UpdateGroupArgs by navArgs()
        val groupModel = bundle.groupModel

        val oldGroupName = groupModel.groupName
        binding.editTextGroupName.setText(oldGroupName)

        binding.updateButton.setOnClickListener {
            val groupName=binding.editTextGroupName
            if(groupName.text.toString().isNotEmpty() && !databaseHelper.findGroup(groupName.text.toString())){
                databaseHelper.updateGroup(groupName.text.toString(),oldGroupName)
                Toast.makeText(requireContext(), "Group updated", Toast.LENGTH_SHORT).show()
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