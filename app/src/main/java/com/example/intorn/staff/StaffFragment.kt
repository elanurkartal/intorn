package com.example.intorn.staff

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.ItemAdapter
import com.example.intorn.ItemModel
import com.example.intorn.MainActivity
import com.example.intorn.ManagerActivity
import com.example.intorn.R
import com.example.intorn.UserActivity


class StaffFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var mainActivity: MainActivity
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff, container, false)
        recyclerView = view.findViewById(R.id.staffRecyclerview)

        // RecyclerView için örnek veri oluştur
        val items = listOf(
            ItemModel(R.drawable.ic_staffs_icon, "Staff"),
          //  ItemModel(R.drawable.ic_staff_roles_icon, "Staff Roles")
        )

        // LayoutManager ve Adapter'i RecyclerView'e bağla
        itemAdapter = ItemAdapter(items)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = itemAdapter
        }
        itemAdapter.setOnClickListener(object : ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: ItemModel) {
                if(model.text=="Staff"){
                    openUserActivity()
                }
                /*else if(model.text=="Staff Roles"){
                    openManagerActivity()
                }*/
            }

        })
        return view
    }
    private fun openUserActivity() {

    }
    private fun openManagerActivity() {

    }

}