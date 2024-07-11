package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentDepartmentBinding


class department : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: FragmentDepartmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var Department_adapter: Department_adapter

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

        val items = databaseHelper.getDepartmentName()
        Department_adapter = Department_adapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2,
                LinearLayoutManager.VERTICAL, false)
            adapter = Department_adapter
        }

    }



    private fun initClickListener(){
        binding.addDepartmentLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addDepartment)
        }
    }
}