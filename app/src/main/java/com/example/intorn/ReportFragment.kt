package com.example.intorn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.intorn.MainActivity.Companion.navController
import com.example.intorn.databinding.FragmentHomeBinding
import com.example.intorn.databinding.FragmentReportBinding

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.xReport.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToSingleOrMultiFragment("X Report")
            findNavController().navigate(action)
        }
        binding.zReport.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToSingleOrMultiFragment("Z Report")
            findNavController().navigate(action)
        }

    }

}