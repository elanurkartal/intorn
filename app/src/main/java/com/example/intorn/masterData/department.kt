package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentDepartmentBinding
import com.example.intorn.databinding.FragmentGroupBinding

class department : Fragment() {
    private lateinit var binding: FragmentDepartmentBinding
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
    private fun initClickListener(){
        binding.addDepartmentLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addDepartment)
        }
    }
}