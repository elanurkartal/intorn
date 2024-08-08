package com.example.intorn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentReportBinding
import com.example.intorn.databinding.FragmentSingleOrMultiBinding
import com.example.intorn.databinding.ReceiptAlertDialogBinding
import com.example.intorn.databinding.XReportDialogBinding
import com.example.intorn.staff.UserUpdateFragmentArgs
import java.util.Locale

class SingleOrMultiFragment : Fragment() {
    private lateinit var binding: FragmentSingleOrMultiBinding
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleOrMultiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.totalRv)
        databaseHelper = DatabaseHelper(requireContext())

        val bundle : SingleOrMultiFragmentArgs by navArgs()
        val singleOrMulti = bundle.raportType

        binding.single.setOnClickListener {
            if (singleOrMulti == "X Report"){
                showAlertDialog()
            }

        }
    }
    private fun showAlertDialog() {
        val customLayout =
            layoutInflater.inflate(R.layout.x_report_dialog, binding.root, false)
        val alertBinding = XReportDialogBinding.bind(customLayout)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(alertBinding.root)
            .setNegativeButton("Select") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun addTotalRv(){
        val newList = databaseHelper.getTotalList()
        reportAdapter = ReportAdapter(newList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportAdapter
        }
    }
}