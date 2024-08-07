package com.example.intorn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentReportBinding
import com.example.intorn.databinding.FragmentSingleOrMultiBinding
import com.example.intorn.databinding.ReceiptAlertDialogBinding
import java.util.Locale

class SingleOrMultiFragment : Fragment() {
    private lateinit var binding: FragmentSingleOrMultiBinding


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
        binding.single.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val customLayout =
            layoutInflater.inflate(R.layout.receipt_alert_dialog, binding.root, false)
        val alertBinding = ReceiptAlertDialogBinding.bind(customLayout)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(alertBinding.root)
            .setNegativeButton("Select") { dialog, which ->
                dialog.dismiss()
            }.show()
    }
}