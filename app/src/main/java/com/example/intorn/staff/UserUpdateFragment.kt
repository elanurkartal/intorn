package com.example.intorn.staff

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
import com.example.intorn.databinding.FragmentAddUserBinding
import com.example.intorn.databinding.FragmentUserUpdateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserUpdateFragment : Fragment() {
    lateinit var binding: FragmentUserUpdateBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())

        val spinner = binding.spinner
        val items = listOf("User", "Manager")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
            
        val bundle : UserUpdateFragmentArgs by navArgs()
        val userModel = bundle.userModel

        val oldName = userModel.userName
        binding.editTextName.setText(oldName)
        binding.spinner.setSelection(items.indexOf(userModel.userType))

        binding.updateSaveButton.setOnClickListener {
            updateUser(
                binding.editTextName.text.toString(),
                oldName,
                binding.editTextPassword.text.toString(),
                binding.editTextPasswordAgain.text.toString(),
                binding.spinner.selectedItem.toString()
            )
        }
        binding.updateUserCancel.setOnClickListener {
            MainActivity.navController.navigate(R.id.userFragment)
        }
    }

    private fun updateUser(newName: String, oldName:String, password: String, passwordAgain: String, type:String) {

        if (newName.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()) {
            if (password != passwordAgain) {
                Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show()
            } else {
                databaseHelper.updateUser(newName, password, type, oldName)
                MainActivity.navController.navigate(R.id.userFragment)
                Toast.makeText(activity, "User updated", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show()
        }
    }


}