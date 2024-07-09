package com.example.intorn.staff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentUserBinding

class userFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentUserBinding.inflate(layoutInflater,container,false)
        binding.addStaffLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addUserFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}