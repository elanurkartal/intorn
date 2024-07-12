package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddArticleBinding
import com.example.intorn.databinding.FragmentAddGroupBinding
import com.example.intorn.databinding.FragmentAddTaxesBinding

class addTaxes : Fragment() {
    lateinit var binding: FragmentAddTaxesBinding
    lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddTaxesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())

        binding.saveButton.setOnClickListener{
            val taxesName=binding.spinner
            val taxesRate = binding.editTextName
            if(taxesName.text.toString().isNotEmpty() && taxesRate.text.toString().isNotEmpty()){
                databaseHelper.insertTaxes(taxesName.text.toString(), taxesRate.text.toString().toDouble())
                Toast.makeText(requireContext(), "Taxes added", Toast.LENGTH_SHORT).show()
                MainActivity.navController.navigate(R.id.department)
            }
            else{
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            MainActivity.navController.navigate(R.id.taxes)
        }
    }

}