package com.example.intorn.masterData.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddArticleBinding


class addArticle : Fragment() {
    lateinit var binding: FragmentAddArticleBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())

        val spinner = binding.spinnerDepartment
        val items = databaseHelper.getDepartments()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val spinnerTax = binding.spinnerTax
        val itemsTax = databaseHelper.getTaxes()
        val adapterTax =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, itemsTax)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTax.adapter = adapterTax

        binding.saveButton.setOnClickListener {
            val departmentId = binding.spinnerDepartment.selectedItem.toString()
            val productNumber = binding.editTextPluNo.text.toString()
            val name = binding.articleName.text.toString()
            val vat = binding.spinnerTax.selectedItem.toString()
            val price = binding.articlePrice.text.toString()
            val stock = binding.editTextArticleStock.text.toString()
            if(departmentId.isNotEmpty()&&productNumber.isNotEmpty()&&name.isNotEmpty()&&
                vat.isNotEmpty()&&price.isNotEmpty()&&stock.isNotEmpty()){

                databaseHelper.insertArticle(databaseHelper.getDepartmentId(departmentId),productNumber,name,vat,price.toDouble(),stock.toInt())
                Toast.makeText(requireContext(), "Article added", Toast.LENGTH_SHORT).show()
                MainActivity.navController.navigate(R.id.article)
            }
            else{
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }

        }
        binding.cancelButton.setOnClickListener {
            MainActivity.navController.navigate(R.id.article)
        }

    }


}