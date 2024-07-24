package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentGroupBinding



class group : Fragment() {


    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var group_adapter: Group_adapter
    private lateinit var binding: FragmentGroupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.group_recyclerview)

        // Verileri yükle ve RecyclerView'e bağla
        loadGroupData()

        // Grup silme işlemi için onClickDeleteItem dinleyicisi
        group_adapter.setOnClickDeleteItem { groupData ->
            databaseHelper.deleteGroup(groupData.groupName)
            // Veriyi güncelledikten sonra RecyclerView'yi yeniden yükle
            loadGroupData()
        }

        // Click listener için fonksiyonu çağır
        initClickListener()
    }

    private fun loadGroupData() {
        val items = databaseHelper.getGroupsName()
        group_adapter = Group_adapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = group_adapter
        }
    }

    private fun initClickListener() {
        binding.addGroupLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addGroup)
        }
    }
}