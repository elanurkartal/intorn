package com.example.intorn.masterData

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.ItemAdapter
import com.example.intorn.ItemModel
import com.example.intorn.MainActivity
import com.example.intorn.R

class DataFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        recyclerView = view.findViewById(R.id.masterData_recyclerview)

        // RecyclerView için örnek veri oluştur
        val items = listOf(
            ItemModel(R.drawable.ic_staffs_icon, "Group"),
            ItemModel(R.drawable.ic_staffs_icon, "Department"),
            ItemModel(R.drawable.ic_staffs_icon, "Article"),
            ItemModel(R.drawable.ic_staffs_icon, "Taxes"),
            //  ItemModel(R.drawable.ic_staff_roles_icon, "Staff Roles")
        )

        // LayoutManager ve Adapter'i RecyclerView'e bağla
        itemAdapter = ItemAdapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2,LinearLayoutManager.VERTICAL, false)
            adapter = itemAdapter
        }
        itemAdapter.setOnClickListener(object : ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: ItemModel) {
                when (model.text) {
                    "Group" -> MainActivity.navController.navigate(R.id.group)
                    "Department" -> MainActivity.navController.navigate(R.id.department)
                    "Article" -> MainActivity.navController.navigate(R.id.article)
                    "Taxes" -> MainActivity.navController.navigate(R.id.taxes)
                }
            }

        })
        return view
    }

}