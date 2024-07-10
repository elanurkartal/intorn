package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentAddArticleBinding
import com.example.intorn.databinding.FragmentAddDepartmentBinding


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

    }


}