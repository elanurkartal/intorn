package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentArticleBinding
import com.example.intorn.databinding.FragmentGroupBinding

class article : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentArticleBinding.inflate(inflater, container, false)
        initClickListener()
        return binding.root
    }
    private fun initClickListener(){
        binding.addArticleLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addArticle)
        }
    }

}