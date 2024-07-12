package com.example.intorn.masterData.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentArticleBinding
import com.example.intorn.masterData.Group_adapter

class article : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.article_recyclerview)

        val items = databaseHelper.getArticles()
        articleAdapter = ArticleAdapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2,
                LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }
    }


    private fun initClickListener(){
        binding.addArticleLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addArticle)
        }
    }
}
