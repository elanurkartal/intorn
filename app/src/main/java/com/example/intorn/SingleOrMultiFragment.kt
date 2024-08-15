package com.example.intorn

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentReportBinding
import com.example.intorn.databinding.FragmentSingleOrMultiBinding
import com.example.intorn.databinding.ReceiptAlertDialogBinding
import com.example.intorn.databinding.XReportDialogBinding
import com.example.intorn.staff.UserUpdateFragmentArgs
import java.util.Locale
import kotlin.math.log

class SingleOrMultiFragment : Fragment() {
    private lateinit var binding: FragmentSingleOrMultiBinding
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var zReportAdapter: ZReportAdapter
    private lateinit var newList: MutableList<ReportModel>
    private lateinit var newZList: MutableList<ZReportModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var loginUser = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleOrMultiBinding.inflate(inflater, container, false)
        sharedPreferences=requireContext().getSharedPreferences("UserInfo", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        loginUser = sharedPreferences.getString("user_name","").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        val bundle : SingleOrMultiFragmentArgs by navArgs()
        val singleOrMulti = bundle.raportType

        binding.single.setOnClickListener {
            if (singleOrMulti == "X Report"){
                insertReport("X")
                showAlertDialogSingle("X")
            }
            else{
                insertReport("Z")
                showAlertDialogSingle("Z")
            }
        }
        binding.multi.setOnClickListener {
            if (singleOrMulti == "X Report"){
                insertReport("X")
                showAlertDialogMulti("X")
            }
        }
    }
    private fun showAlertDialogSingle(type: String) {
        val customLayout =
            layoutInflater.inflate(R.layout.x_report_dialog, binding.root, false)
        addTotalRvSingle(customLayout,type)
        val userType: TextView = customLayout.findViewById(R.id.textView8)
        val reportId: TextView = customLayout.findViewById(R.id.textView11)
        userType.text = "User Type:"+databaseHelper.getUserRole(loginUser)
        reportId.text = "ReportID:"+databaseHelper.getReportCount(type).toString()
        val alertBinding = XReportDialogBinding.bind(customLayout)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(alertBinding.root)
            .setNegativeButton("Select") { dialog, which ->
                dialog.dismiss()
            }.show()
    }
    private fun showAlertDialogMulti(type: String) {
        val customLayout =
            layoutInflater.inflate(R.layout.x_report_dialog, binding.root, false)
        addTotalRvMulti(customLayout)
        val userType: TextView = customLayout.findViewById(R.id.textView8)
        val reportId: TextView = customLayout.findViewById(R.id.textView11)
        userType.text = "User Type:"+databaseHelper.getUserRole(loginUser)
        reportId.text = "ReportID:"+databaseHelper.getReportCount(type).toString()
        val alertBinding = XReportDialogBinding.bind(customLayout)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(alertBinding.root)
            .setNegativeButton("Select") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun addTotalRvSingle(view: View,type: String){
        recyclerView = view.findViewById(R.id.totalRv)
        val bar = "Bar"
        val card = "Card"
        val other = "Other"

        if(databaseHelper.getTotalListFromPaymentType(bar,databaseHelper.getUserIdFromUserName(loginUser)).amount!=0){
            addRvSingle(bar,type)
        }
        if (databaseHelper.getTotalListFromPaymentType(card,databaseHelper.getUserIdFromUserName(loginUser)).amount!=0){
            addRvSingle(card,type)
        }
        if (databaseHelper.getTotalListFromPaymentType(other,databaseHelper.getUserIdFromUserName(loginUser)).amount!=0){
            addRvSingle(other,type)
        }
    }
    private fun addTotalRvMulti(view: View){
        newList = ArrayList()
        newZList = ArrayList()
        recyclerView = view.findViewById(R.id.totalRv)
        val bar = "Bar"
        val card = "Card"
        val other = "Other"

        if(databaseHelper.getTotalListFromPaymentTypeMulti(bar).amount!=0){
            addRvMulti(bar)
        }
        if (databaseHelper.getTotalListFromPaymentTypeMulti(card).amount!=0){
            addRvMulti(card)
        }
        if (databaseHelper.getTotalListFromPaymentTypeMulti(other).amount!=0){
            addRvMulti(other)
        }
    }

    private fun addRvSingle(payment: String, type: String){
        newList = ArrayList()
        newZList = ArrayList()
        if (type == "X"){
            newList.add(databaseHelper.getTotalListFromPaymentType(payment, databaseHelper.getUserIdFromUserName(loginUser)))
            reportAdapter = ReportAdapter(newList)
            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1, LinearLayoutManager.VERTICAL, false)
                adapter = reportAdapter
            }
            reportAdapter.notifyDataSetChanged()
        }
        else{
            for (model in databaseHelper.getSellingProcess()){
                newZList.add(model)
            }
            zReportAdapter = ZReportAdapter(newZList)
            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1, LinearLayoutManager.VERTICAL, false)
                adapter = zReportAdapter
            }
            zReportAdapter.notifyDataSetChanged()
        }
    }
    private fun addRvMulti(type: String){
        newList.add(databaseHelper.getTotalListFromPaymentTypeMulti(type))
        reportAdapter = ReportAdapter(newList)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.HORIZONTAL, false)
            adapter = reportAdapter
        }
        reportAdapter.notifyDataSetChanged()
    }

    private fun insertReport(type: String){
        if (databaseHelper.findReport(type)){
            databaseHelper.updateReport(type)
        }
        else{
            databaseHelper.insertReport(type)
        }
    }
}