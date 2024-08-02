package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentDepartmentBinding
import java.util.Locale


class department : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: FragmentDepartmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var Department_adapter: Department_adapter
    private lateinit var departmentArrayListTmp: ArrayList<Department_model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDepartmentBinding.inflate(inflater, container, false)
        initClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper= DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.department_recyclerview)
        departmentArrayListTmp = ArrayList()

        loadGroupData()

        // Grup silme işlemi için onClickDeleteItem dinleyicisi
        Department_adapter.setOnClickDeleteItem { departmentData ->
            databaseHelper.deleteDepartment(departmentData.department_name)
            // Veriyi güncelledikten sonra RecyclerView'yi yeniden yükle
            loadGroupData()
        }
        /*
        Department_adapter.setOnClickUpdateItem(object : Department_adapter.OnClickListener{
            override fun onClick(position: Int, model: Department_model) {
                val action = departmentDirections.actionDepartmentToUpdateDepartment(model)
                findNavController().navigate(action)
                //MainActivity.navController.navigate(R.id.userUpdateFragment)
            }
        })

         */

        // Click listener için fonksiyonu çağır
        initClickListener()

    }
    private fun loadGroupData() {
        val items = databaseHelper.getDepartmentName()
        Department_adapter = Department_adapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = Department_adapter
        }
        binding.searchViewDepartment.onActionViewExpanded()
        binding.searchViewDepartment.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    departmentArrayListTmp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        items.forEach {
                            if (it.department_name.lowercase(Locale.getDefault()).contains(searchText)) {
                                departmentArrayListTmp.add(it)
                            }
                        }
                        Department_adapter.updateItems(departmentArrayListTmp)
                    } else {
                        departmentArrayListTmp.clear()
                        departmentArrayListTmp.addAll(items)
                        Department_adapter.updateItems(items)
                    }
                    return false
                }
            })
    }
    private fun initClickListener(){
        binding.addDepartmentLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addDepartment)
        }
    }
}