package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentGroupBinding
import java.util.Locale


class group : Fragment() {


    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var group_adapter: Group_adapter
    private lateinit var binding: FragmentGroupBinding
    private lateinit var groupArrayListTmp: ArrayList<Group_model>


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
        groupArrayListTmp = ArrayList()

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
        binding.searchViewGroup.onActionViewExpanded()
        binding.searchViewGroup.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    groupArrayListTmp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        items.forEach {
                            if (it.groupName.lowercase(Locale.getDefault()).contains(searchText)) {
                                groupArrayListTmp.add(it)
                            }
                        }
                        group_adapter.updateItems(groupArrayListTmp)
                    } else {
                        groupArrayListTmp.clear()
                        groupArrayListTmp.addAll(items)
                        group_adapter.updateItems(items)
                    }
                    return false
                }
            })
    }

    private fun initClickListener() {
        binding.addGroupLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addGroup)
        }
    }
}