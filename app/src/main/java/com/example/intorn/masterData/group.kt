package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intorn.HomeFragment
import com.example.intorn.R
import com.example.intorn.databinding.FragmentGroupBinding
import com.example.intorn.databinding.FragmentStaffBinding
import com.google.android.material.navigation.NavigationView


class group : Fragment() {

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
    private fun initClickListener(){
          }

}